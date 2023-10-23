package com.famy.us.feature.note.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.famy.us.core.extensions.logD
import com.famy.us.core.ui.ReordableList
import com.famy.us.domain.model.HomeTask

@Composable
fun TaskList(
    tasksProvider: () -> List<HomeTask>,
    itemDragged: HomeTask?,
    onDragItem: (Int?) -> Unit,
    onMoveItem: (from: Int, to: Int) -> Unit,
    onClickCard: (HomeTask) -> Unit,
    onStop: () -> Unit,
    onDeleteClicked: (HomeTask) -> Unit,
    onEditClicked: (HomeTask) -> Unit,
) {
    var verticalTranslation: Float by remember {
        mutableStateOf(0f)
    }
    var itemToShowOption by remember {
        mutableStateOf(-1)
    }
    val list = tasksProvider()
    ReordableList(
        list,
        onDrag = { value ->
            verticalTranslation = value
        },
        onLongPress = { itemIndex ->
            itemToShowOption = itemIndex
        },
        onDragStart = { itemIndex ->
            onDragItem(itemIndex)
        },
        onStop = onStop,
        onMove = onMoveItem,
    ) { item, index ->
        TaskItem(
            item,
            onClickCard = { showingConent ->
                logD { "Clicking" }
                if (showingConent) {
                    onClickCard(item)
                } else {
                    itemToShowOption = -1
                }
            },
            showContentProvider = {
                val show = itemToShowOption != index
                show
            },
            isDragged = if (itemDragged == null) {
                false
            } else {
                itemDragged!!.id == item.id
            },
            onDeleteIsClicked = {
                onDeleteClicked(item)
            },
            onEditIsClicked = {
                onEditClicked(item)
            },
            verticalTranslation = verticalTranslation.toInt(),
        )
    }
}
