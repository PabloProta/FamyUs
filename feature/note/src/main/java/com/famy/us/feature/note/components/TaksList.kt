package com.famy.us.feature.note.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask

@Composable
fun TaskList(
    tasksProvider: () -> List<HomeTask>,
    onClickCard: (HomeTask) -> Unit,
    onDeleteClicked: (HomeTask) -> Unit,
    onEditClicked: (HomeTask) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 78.dp),
    ) {
        this.items(tasksProvider(), key = { task -> task.id }) { task ->
            TaskItem(
                task,
                onClickCard = {
                    onClickCard(task)
                },
                onDeleteIsClicked = {
                    onDeleteClicked(task)
                },
                onEditIsClicked = {
                    onEditClicked(task)
                },
            )
        }
    }
}
