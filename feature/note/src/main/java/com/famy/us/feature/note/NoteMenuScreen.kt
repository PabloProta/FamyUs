package com.famy.us.feature.note

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.sharp.DeleteForever
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.famy.us.core.extensions.logD
import com.famy.us.core.ui.CustomDialog
import com.famy.us.domain.model.HomeTask
import org.koin.androidx.compose.koinViewModel

@Suppress("LongMethod")
@Composable
internal fun NoteMenuScreen(viewModel: NoteMenuViewModel = koinViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        when {
            state.value.isLoading -> {
                logD { "LoadingTasks" }
                ShowProgress()
            }
            state.value.isLoading.not() -> {
                logD { "NoteMenu" }
                NoteMenuScreenContent(
                    onAddTaskClicked = {
                        viewModel.perform(NoteScreenIntent.AddTask)
                    },
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 78.dp),
                    ) {
                        items(state.value.listTask) { task ->
                            TaskItem(
                                task,
                                onClickCard = {
                                    viewModel.perform(NoteScreenIntent.ShowTaskContent(task))
                                },
                                onDeleteIsClicked = {
                                    viewModel.perform(
                                        NoteScreenIntent.DeleteTask(task),
                                    )
                                },
                                onEditIsClicked = {
                                    viewModel.perform(
                                        NoteScreenIntent.EditTask(task),
                                    )
                                },
                            )
                        }
                    }
                }
                val managingTask = state.value.managingTask
                HomeTaskDialog(
                    showDialog = state.value.showDialog,
                    task = state.value.managingTask,
                    onDismissDialog = {
                        viewModel.perform(NoteScreenIntent.DismissDialog)
                    },
                    onTypeText = {
                        viewModel.perform(NoteScreenIntent.TypingText(it))
                    },
                    onSlideSlider = {
                        viewModel.perform(NoteScreenIntent.SlidingSlider(it.toInt()))
                    },
                    onSaveTask = {
                        viewModel.perform(
                            NoteScreenIntent.SaveTask(
                                task = it,
                                isNewOne = state.value.showDialog.isAddingTask,
                            ),
                        )
                    },
                    onAllowEditMode = {
                        viewModel.perform(
                            NoteScreenIntent.EditTask(managingTask),
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun ShowProgress() {
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

@Composable
internal fun NoteMenuScreenContent(
    onAddTaskClicked: () -> Unit,
    onShowTasks: @Composable () -> Unit,
) {
    Box {
        onShowTasks()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                FloatingActionButton(
                    modifier = Modifier,
                    onClick = onAddTaskClicked,
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
                }
            }
        }
    }
}

@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeTaskDialog(
    showDialog: ShowDialog,
    task: HomeTask,
    onDismissDialog: () -> Unit,
    onTypeText: (String) -> Unit,
    onSlideSlider: (Float) -> Unit,
    onSaveTask: (HomeTask) -> Unit,
    onAllowEditMode: () -> Unit,
) {
    val context = LocalContext.current
    if (showDialog.shouldShowDialog) {
        CustomDialog(onDismissDialog = onDismissDialog) {
            if (!showDialog.isEditingTask) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(
                        modifier = Modifier,
                        onClick = onAllowEditMode,
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Edit,
                            contentDescription = "edit ${task.name}",
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .size(height = 10.dp, width = 0.dp),
            )
            OutlinedTextField(
                value = task.name,
                onValueChange = onTypeText,
                enabled = showDialog.isEditingTask,
                maxLines = 1,
                label = {
                    Text(text = "Tarefa")
                },
                placeholder = {
                    Text(text = "digite o nome da tarefa")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { saveTask(task, showDialog.isAddingTask, onSaveTask) },
                ),
            )
            Slider(
                value = task.point.toFloat(),
                onValueChange = onSlideSlider,
                valueRange = 0f..100f,
                enabled = showDialog.isEditingTask,
            )
            Text(
                text = task.point.toString(),
            )

            if (showDialog.isEditingTask) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(
                        content = {
                            Icon(imageVector = Icons.Rounded.Check, contentDescription = "ds")
                        },
                        onClick = {
                            if (task.name.isEmpty() || task.point < 1) {
                                Toast.makeText(context, "Preencha os campos", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                saveTask(task, showDialog.isAddingTask, onSaveTask)
                            }
                        },
                    )
                }
            }
        }
    }
}

private fun saveTask(task: HomeTask, isAddingTask: Boolean, onSaveTask: (HomeTask) -> Unit) {
    if (isAddingTask) {
        onSaveTask(HomeTask(0, task.name, task.point, false))
    } else {
        onSaveTask(task)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskItem(
    task: HomeTask,
    onClickCard: () -> Unit,
    onEditIsClicked: () -> Unit,
    onDeleteIsClicked: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp,
            pressedElevation = 0.dp,
        ),
        onClick = onClickCard,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .widthIn(0.dp, 140.dp),
                text = "Tarefa: ${task.name}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(
                modifier = Modifier.size(30.dp),
            )
            Text(
                text = "pontos: ${task.point}",
            )
            Spacer(
                modifier = Modifier
                    .weight(1f),
            )
            IconButton(
                modifier = Modifier,
                onClick = onEditIsClicked,
            ) {
                Icon(imageVector = Icons.Sharp.Edit, contentDescription = "edit ${task.name}")
            }

            IconButton(
                modifier = Modifier,
                onClick = onDeleteIsClicked,
            ) {
                Icon(
                    imageVector = Icons.Sharp.DeleteForever,
                    contentDescription = "delete ${task.name}",
                )
            }
        }
    }
}
