package com.famy.us.feature.note.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.famy.us.core.ui.ReordableList
import com.famy.us.domain.model.HomeTask

@Composable
fun TaskList(
    tasksProvider: () -> List<HomeTask>,
    itemDragged: HomeTask?,
    notesSelectedProvider: () -> List<Int>,
    onDragItem: (Int?) -> Unit,
    onMoveItem: (from: Int, to: Int) -> Unit,
    onLongPress: (itemSelected: Int) -> Unit,
    onSelectCard: (itemSelected: Int) -> Unit,
    onClickCard: (HomeTask) -> Unit,
    onStop: () -> Unit,
) {
    var verticalTranslation: Float by remember {
        mutableStateOf(0f)
    }
    val notesSelected = notesSelectedProvider()
    val isSelecting = notesSelected.isNotEmpty()
    val list = tasksProvider()
    if (isSelecting) {
        ReordableList(
            list,
            onDrag = { value ->
                verticalTranslation = value
            },
            isReording = false,
            onLongPress = {
                onLongPress(it)
            },
            onDragStart = { itemIndex ->
                onDragItem(itemIndex)
            },
            onStop = onStop,
            onMove = onMoveItem,
        ) { item, index ->
            val isSelected = notesSelected.firstOrNull { it == index } != null
            TaskItem(
                item,
                onClickCard = {
                    onSelectCard(index)
                },
                isSelected = isSelected,
                isDragged = if (itemDragged == null) {
                    false
                } else {
                    itemDragged!!.id == item.id
                },
                verticalTranslation = verticalTranslation.toInt(),
                isSelecting = isSelecting,
            )
        }
    } else {
        ReordableList(
            list,
            onDrag = { value ->
                verticalTranslation = value
            },
            isReording = true,
            onLongPress = {
                onLongPress(it)
            },
            onDragStart = { itemIndex ->
                onDragItem(itemIndex)
            },
            onStop = onStop,
            onMove = onMoveItem,
        ) { item, index ->
            val isSelected = notesSelected.firstOrNull { it == index } != null
            TaskItem(
                item,
                onClickCard = {
                    onClickCard(item)
                },
                isSelected = isSelected,
                isDragged = if (itemDragged == null) {
                    false
                } else {
                    itemDragged!!.id == item.id
                },
                verticalTranslation = verticalTranslation.toInt(),
                isSelecting = isSelecting,
            )
        }
    }
}
