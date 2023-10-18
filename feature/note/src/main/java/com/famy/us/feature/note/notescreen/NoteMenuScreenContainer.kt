package com.famy.us.feature.note.notescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.extensions.logD
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.components.NoteMenuScreenContent
import com.famy.us.feature.note.components.TaskList
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState
import org.koin.androidx.compose.koinViewModel

@Suppress("LongMethod")
@Composable
internal fun NoteMenuScreenContainer(
    viewModel: NoteMenuViewModel = koinViewModel(),
    onNavigate: (String) -> Unit,
) {
    val uiState by remember {
        viewModel.uiState
    }
    NoteMenuScreen(
        uiState = uiState,
        onNavigate = onNavigate,
        performAction = { action ->
            viewModel.perform(action)
        },
    )
}

@Composable
internal fun NoteMenuScreen(
    uiState: NoteScreenState,
    onNavigate: (String) -> Unit,
    performAction: (NoteScreenIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        ShowProgress(
            isLoadingProvider = {
                uiState.isLoading
            },
        )

        if (uiState.isLoading.not()) {
            when {
                uiState.isAddingTask -> {
                    // TODO - also recomposing unnecessary.
                }

                uiState.goingToShowTaskContent > 0 -> {
                    // TODO - we should navigate here check the bug later.
                }
            }
        }

        Text(
            text = "Minhas\nTarefas",
            style = MaterialTheme.typography.displayLarge,
        )
        Spacer(modifier = Modifier.size(32.dp))
        NoteMenuScreenContent(
            onAddTaskClicked = {
                onNavigate("create_task")
                performAction(NoteScreenIntent.AddTask)
            },
        ) {
            TaskList(
                tasksProvider = {
                    uiState.showingTaskList
                },
                itemDragged = uiState.draggingItem,
                onMoveItem = { from, to ->
                    performAction(NoteScreenIntent.MoveNote(from, to))
                },
                onDragItem = { itemDragged ->
                    performAction(NoteScreenIntent.DragNote(itemDragged))
                },
                onClickCard = {
                    onNavigate("note_content/${it.id}")
                    // TODO - A Bug here that the state are recomposing this method every time.
//                    performAction(NoteScreenIntent.ShowTaskContent(it.id))
                },
                onDeleteClicked = {
                    performAction(NoteScreenIntent.DeleteTask(it))
                },
                onEditClicked = {
                    performAction(NoteScreenIntent.EditTask(it))
                },
                onStop = {
                    performAction(NoteScreenIntent.StopDrag)
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

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun NoteMenuScreenPreview() {
    val taskDefault = HomeTask.Empty.copy(
        id = 0,
        point = 50,
    )
    NoteMenuScreen(
        uiState = NoteScreenState.Idle.copy(
            isLoading = false,
            showingTaskList = listOf(
                taskDefault.copy(name = "task 1"),
                taskDefault.copy(id = 1, name = "task 2"),
                taskDefault.copy(id = 2, name = "task 3"),
            ),
        ),
        onNavigate = {},
        performAction = {},
    )
}
