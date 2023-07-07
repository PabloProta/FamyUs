package com.famy.us.feature.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.components.HomeTaskDialog
import com.famy.us.feature.note.components.NoteMenuScreenContent
import com.famy.us.feature.note.components.TaskItem
import org.koin.androidx.compose.koinViewModel

@Suppress("LongMethod")
@Composable
internal fun NoteMenuScreen(viewModel: NoteMenuViewModel = koinViewModel()) {
    val uiState by remember {
        viewModel.uiState
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ShowProgress(
            isLoadingProvider = {
                uiState.isLoading
            },
        )
        HomeTaskDialog(
            taskProvider = {
                uiState.managingTask
            },
            showDialogProvider = {
                uiState.showDialog
            },
            onDismissDialog = {
                viewModel.perform(NoteScreenIntent.DismissDialog)
            },
            onEditClicked = {
                viewModel.perform(NoteScreenIntent.EditTask(uiState.managingTask))
            },
            onSaveClicked = {
                viewModel.perform(NoteScreenIntent.SaveTask(it))
            },
        )

        NoteMenuScreenContent(
            onAddTaskClicked = {
                viewModel.perform(NoteScreenIntent.AddTask)
            },
        ) {
            TaskList(
                tasksProvider = {
                    uiState.listTask
                },
                onClickCard = {
                    viewModel.perform(NoteScreenIntent.ShowTaskContent(it))
                },
                onDeleteClicked = {
                    viewModel.perform(NoteScreenIntent.DeleteTask(it))
                },
                onEditClicked = {
                    viewModel.perform(NoteScreenIntent.EditTask(it))
                },
            )
        }
    }
}

@Composable
fun TaskList(
    tasksProvider: () -> List<HomeTask>,
    onClickCard: (HomeTask) -> Unit,
    onDeleteClicked: (HomeTask) -> Unit,
    onEditClicked: (HomeTask) -> Unit,
) {
    val tasks = tasksProvider()
    LazyColumn(
        contentPadding = PaddingValues(bottom = 78.dp),
    ) {
        items(tasks, key = { task -> task.id }) { task ->
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

@Composable
fun ShowProgress(isLoadingProvider: () -> Boolean) {
    if (isLoadingProvider()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                strokeWidth = 2.dp,
            )
        }
    }
}
