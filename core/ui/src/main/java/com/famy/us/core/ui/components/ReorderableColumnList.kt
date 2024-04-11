package com.famy.us.core.ui.components

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.core.extensions.move
import com.famy.us.core.ui.pointerinput.detectDragGesturesAfterLongPress
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Component that has list that could be sorted by dragging it.
 *
 * @param items the list items.
 * @param contentPaddingValues the list padding.
 * @param onDrag when some item are being dragged
 * value : is the amount dragged based on the item dragged position negative value means it was
 * dragged on top direction, positive value means the item was dragged on bottom direction
 * itemDragged: is the index according to the visible items on the list (this might
 * change according with overscroll)
 * @param onMove when some item hover on another one.
 * fromIndex: The index that started to move.
 * toIndex: The index of the visible item that the dragged item is switching.
 * @param onDragStop when the drag action is stopped.
 * @param itemContent the content of the list.
 */
@Composable
fun <T> ReorderableColumnList(
    modifier: Modifier = Modifier,
    items: List<T>,
    isReordering: Boolean = true,
    threshold: Int = 80,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
    onDrag: (value: Float) -> Unit = {},
    onDragStart: (itemDragged: Int?) -> Unit = {},
    onMove: (fromIndex: Int, toIndex: Int) -> Unit = { _, _ -> },
    onDragStop: (items: List<T>) -> Unit = {},
    itemContent: @Composable (item: T, index: Int) -> Unit,
) {
    var verticalOffset by remember { mutableFloatStateOf(0f) }
    var itemInfoDragged by remember {
        mutableStateOf<LazyListItemInfo?>(null)
    }
    var overScrollJob by remember {
        mutableStateOf<Job?>(null)
    }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var listMutable: List<T> by remember {
        mutableStateOf(items)
    }
    var currentHovered: LazyListItemInfo? by remember {
        mutableStateOf(null)
    }

    listMutable = items

    fun checkForOverScroll(): Float = itemInfoDragged?.let {
        val startOffset = it.offset + verticalOffset
        val endOffset = it.offsetEnd + verticalOffset
        val viewPortStart = listState.layoutInfo.viewportStartOffset
        val viewPortEnd = listState.layoutInfo.viewportEndOffset

        when {
            verticalOffset > 0 -> (endOffset - viewPortEnd).takeIf { diff -> diff > 0 }
            verticalOffset < 0 -> (startOffset - viewPortStart).takeIf { diff -> diff < 0 }
            else -> null
        }
    } ?: 0f

    fun resetDrag() {
        currentHovered = null
        verticalOffset = 0f
        itemInfoDragged = null
        overScrollJob?.cancel()
        onDragStop(listMutable)
    }

    if (isReordering) {
        LazyColumn(
            modifier = modifier
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        pass = PointerEventPass.Initial,
                        onDrag = { change, dragAmount ->
                            change.consume()
                            if (dragAmount.y > 1 || dragAmount.y < -1) {
                                verticalOffset += dragAmount.y
                            }
                            onDrag(verticalOffset)
                            val listItemVisible = listState.layoutInfo.visibleItemsInfo
                            itemInfoDragged?.getCurrentHoveredItem(
                                threshold,
                                verticalOffset,
                                listItemVisible,
                            )?.also { currentHoveredItem ->
                                if (currentHovered?.index == currentHoveredItem.index) return@also
                                val currentItem = itemInfoDragged ?: return@also
                                currentHovered = currentHoveredItem
                                listMutable = listMutable.move(
                                    currentItem.index,
                                    currentHoveredItem.index,
                                )
                                onMove(currentItem.index, currentHoveredItem.index)
                                itemInfoDragged = currentHoveredItem
                                verticalOffset = 0f
                            }

                            if (overScrollJob?.isActive == true) {
                                return@detectDragGesturesAfterLongPress
                            }

                            checkForOverScroll()
                                .takeIf { offset -> offset != 0f }
                                ?.let { offset ->
                                    overScrollJob = scope.launch {
                                        listState.scrollBy(offset)
                                    }
                                } ?: kotlin.run { overScrollJob?.cancel() }
                        },
                        onDragStart = { offset ->
                            val itemOffset = offset.y.toInt()
                            listState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
                                itemOffset in item.offset..item.offsetEnd
                            }?.also {
                                itemInfoDragged = it
                                onDragStart(itemInfoDragged?.index)
                            }
                        },
                        onDragEnd = { resetDrag() },
                        onDragCancel = { resetDrag() },
                    )
                },
            contentPadding = contentPaddingValues,
            state = listState,
        ) {
            this.itemsIndexed(items = listMutable, key = { index, _ -> index }) { index, item ->
                val isDragging = index == itemInfoDragged?.index
                Column(
                    modifier = Modifier
                        .zIndex(if (isDragging) 1f else 0f)
                        .offset {
                            if (isDragging) {
                                IntOffset(0, verticalOffset.toInt())
                            } else {
                                IntOffset.Zero
                            }
                        },
                ) {
                    itemContent(item, index)
                }
            }
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPaddingValues,
            state = listState,
        ) {
            this.itemsIndexed(items = listMutable, key = { index, _ -> index }) { index, item ->
                itemContent(item, index)
            }
        }
    }
}

private fun LazyListItemInfo.getCurrentHoveredItem(
    threshold: Int,
    verticalOffset: Float,
    itemsVisible: List<LazyListItemInfo>,
): LazyListItemInfo? {
    val startOffset = offset + verticalOffset
    val endOffset = offsetEnd + verticalOffset
    val limitedEndOffset = endOffset.toInt() - threshold
    val limitedStartOffset = startOffset.toInt() + threshold
    return itemsVisible
        .firstOrNull { item ->
            if (item.index == index) {
                false
            } else {
                val itemRange = item.range()
                limitedEndOffset in itemRange ||
                    limitedStartOffset in itemRange
            }
        }
}

/**
 * Method to get the item offset range.
 */
internal fun LazyListItemInfo.range() = offset..offsetEnd

internal val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size

@Composable
fun ReorderListColumnUsage() {
    val list = List(100) { "item : $it" }
    ReorderableColumnList(
        items = list,
    ) { item, index ->
        Column(
            modifier = Modifier
                .padding(12.dp),
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }
    }
}
