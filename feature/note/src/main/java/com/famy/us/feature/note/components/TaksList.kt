package com.famy.us.feature.note.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.ReorderableColumnList
import com.famy.us.domain.model.HomeTask

@Composable
fun TaskList(
    tasksProvider: () -> List<HomeTask>,
    isReordering: Boolean,
    notesSelectedProvider: () -> List<Int>,
    onDragItem: (Int?) -> Unit,
    onMoveItem: (from: Int, to: Int) -> Unit,
    onSelectCard: (itemSelected: Int) -> Unit,
    onClickCard: (HomeTask) -> Unit,
    onStop: () -> Unit,
) {
    val notesSelected = notesSelectedProvider()
    val isSelecting = notesSelected.isNotEmpty() && !isReordering
    val list = tasksProvider()
    ReorderableColumnList(
        modifier = Modifier
            .padding(if (isReordering) 24.dp else 0.dp),
        items = list,
        isReordering = isReordering,
        onDragStart = { itemIndex ->
            onDragItem(itemIndex)
        },
        onDragStop = { onStop() },
        onMove = onMoveItem,
    ) { item, index ->
        val isSelected = notesSelected.firstOrNull { it == index } != null
        TaskItem(
            item,
            onClickCard = {
                if (!isSelecting) {
                    onClickCard(item)
                } else {
                    onSelectCard(index)
                }
            },
            isSelected = isSelected,
            isSelecting = isSelecting,
            isReordering = isReordering,
            onLongPress = {
                onSelectCard(index)
            },
        )
    }
}
