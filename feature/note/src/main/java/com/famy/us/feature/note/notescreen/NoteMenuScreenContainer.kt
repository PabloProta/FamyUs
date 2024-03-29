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
import com.famy.us.core.utils.navigation.Navigator
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.components.NoteMenuScreenContent
import com.famy.us.feature.note.components.SelectNoteOption
import com.famy.us.feature.note.components.TaskList
import com.famy.us.feature.note.navigation.NoteMenuNavigation
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState
import org.koin.androidx.compose.koinViewModel

@Suppress("LongMethod")
@Composable
internal fun NoteMenuScreenContainer(
    viewModel: NoteMenuViewModel = koinViewModel(),
    onNavigate: (Navigator) -> Unit,
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
    onNavigate: (Navigator) -> Unit,
    performAction: (NoteScreenIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
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
                    onNavigate(Navigator.NavigateTo(NoteMenuNavigation.CreateTask))
                }

                uiState.goingToShowTaskContent > 0 -> {
                    // TODO - also recomposing unnecessary.
                    onNavigate(
                        Navigator.NavigateTo(
                            NoteMenuNavigation.NoteContent(
                                uiState.goingToShowTaskContent.toString(),
                            ),
                        ),
                    )
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
                performAction(NoteScreenIntent.AddTask)
            },
        ) {
            Column {
                SelectNoteOption(
                    isReordering = uiState.reorderingList,
                    isSelecting = uiState.selectingNotes.isNotEmpty(),
                    isAllCheckedProvider = {
                        uiState.selectingNotes.size == uiState.showingTaskList.size
                    },
                    onClickDone = {
                        performAction(NoteScreenIntent.DoneNotes(uiState.selectingNotes))
                    },
                    onClickDelete = {
                        performAction(NoteScreenIntent.DeleteNotes(uiState.selectingNotes))
                    },
                    onCheckClicked = {
                        performAction(NoteScreenIntent.SelectAllNotes(it))
                    },
                    onClickToReorder = {
                        performAction(NoteScreenIntent.ReorderList)
                    },
                )

                TaskList(
                    tasksProvider = {
                        uiState.showingTaskList
                    },
                    isReordering = uiState.reorderingList,
                    notesSelectedProvider = {
                        uiState.selectingNotes
                    },
                    onMoveItem = { from, to ->
                        performAction(NoteScreenIntent.MoveNote(from, to))
                    },
                    onDragItem = { itemDragged ->
                        performAction(NoteScreenIntent.DragNote(itemDragged))
                    },
                    onClickCard = {
                        // TODO - A Bug here that the state are recomposing this method every time.
                        performAction(NoteScreenIntent.ShowTaskContent(it.id))
                    },
                    onStop = {
                        performAction(NoteScreenIntent.StopDrag)
                    },
                    onSelectCard = {
                        performAction(NoteScreenIntent.NoteSelected(it))
                    },
                )
            }
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
