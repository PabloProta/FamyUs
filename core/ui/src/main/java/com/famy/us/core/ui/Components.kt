package com.famy.us.core.ui

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun CustomDialog(onDismissDialog: () -> Unit, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = onDismissDialog) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center,
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    content()
                }
            }
        }
    }
}

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
 * @param onStop when the drag action is stopped.
 * @param itemContent the content of the list.
 */
@Composable
fun <T> ReordableList(
    items: List<T>,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
    onDrag: (value: Float) -> Unit,
    onDragStart: (itemDragged: Int?) -> Unit,
    onMove: (fromIndex: Int, toIndex: Int) -> Unit,
    onStop: () -> Unit = {},
    itemContent: @Composable (item: T, index: Int) -> Unit,
) {
    val listState = rememberLazyListState()
    var verticalOffset by remember { mutableFloatStateOf(0f) }
    var itemInfoDragged by remember {
        mutableStateOf<LazyListItemInfo?>(null)
    }
    var overScrollJob by remember {
        mutableStateOf<Job?>(null)
    }
    val scope = rememberCoroutineScope()

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

    LazyColumn(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        verticalOffset += dragAmount.y
                        onDrag(verticalOffset)
                        listState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
                            val itemRange = item.offset..item.offsetEnd
                            val currentItem = itemInfoDragged ?: return@firstOrNull false
                            val currentItemRange = currentItem.offset..currentItem.offsetEnd
                            if (currentItemRange == itemRange) {
                                false
                            } else {
                                val startOffset = currentItem.offset + verticalOffset
                                val endOffset = currentItem.offsetEnd + verticalOffset
                                val threshold = 40.dp
                                endOffset.toInt() - threshold.roundToPx() in itemRange ||
                                    startOffset.toInt() + threshold.roundToPx() in itemRange
                            }
                        }?.also { currentHoveredItem ->
                            val currentItem = itemInfoDragged ?: return@also
                            if (currentHoveredItem.index != currentItem.index) {
                                onMove(currentItem.index, currentHoveredItem.index)
                                itemInfoDragged = currentHoveredItem
                                verticalOffset = 0f
                            }
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
                    onDragEnd = {
                        verticalOffset = 0f
                        itemInfoDragged = null
                        overScrollJob?.cancel()
                        onStop()
                    },
                    onDragCancel = {
                        verticalOffset = 0f
                        itemInfoDragged = null
                        overScrollJob?.cancel()
                        onStop()
                    },
                )
            },
        contentPadding = contentPaddingValues,
        state = listState,
    ) {
        this
            .itemsIndexed(items = items) { index, item ->
                itemContent(item, index)
            }
    }
}

internal val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size
