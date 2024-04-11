package com.famy.us.feature.note.taskContent

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.components.CustomDialog
import com.famy.us.core.utils.navigation.Navigator
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.components.SimpleTextField
import com.famy.us.feature.note.createNote.components.DefineNoteScoreScreen
import com.famy.us.feature.note.notescreen.NoteMenuViewModel
import com.famy.us.feature.note.notescreen.ShowProgress
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskContentProvider(
    taskId: Int,
    navigateTo: (Navigator) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TaskContentContainer(taskId = taskId, onNavigate = navigateTo)
    }
}

@Composable
internal fun TaskContentContainer(
    viewModel: TaskContentViewModel = koinViewModel(),
    noteMenuViewModel: NoteMenuViewModel = koinViewModel(),
    onNavigate: (Navigator) -> Unit,
    taskId: Int,
) {
    val uiState by remember {
        viewModel.uiState
    }

    if (uiState.deleting || uiState.finishing) {
        noteMenuViewModel.perform(NoteScreenIntent.DismissTaskContent)
        /*
            TODO - I cant do the dismiss action here, because apparently compose has an issue that
             forces our composable recompose every time we make an navigation with the navigation
             lib, the issue tracker is here: https://issuetracker.google.com/issues/302212882
             for now I'll ignore this statements and do nothing until google answers.
         */
    } else if (uiState.onContent == null) {
        viewModel.perform(TaskContentScreenIntent.LoadTask(taskId))
    }
    BackHandler {
        onNavigate(Navigator.PopBackStack)
    }
    TaskContent(
        uiState = uiState,
        onNavigate = onNavigate,
        performAction = {
            viewModel.perform(it)
        },
    )
}

@Composable
private fun TaskContent(
    uiState: TaskContentScreenState,
    onNavigate: (Navigator) -> Unit,
    performAction: (TaskContentScreenIntent) -> Unit,
) {
    fun dismissContent() {
        onNavigate(Navigator.PopBackStack)
    }

    ShowProgress(
        isLoadingProvider = {
            uiState.loading
        },
    )
    if (uiState.loading.not()) {
        when {
            uiState.onContent != null -> {
                OnTaskContent(
                    onTask = { uiState.onContent },
                    onEditing = { uiState.editing },
                    onEditClicked = {
                        performAction(TaskContentScreenIntent.EditTask)
                    },
                    onSave = {
                        performAction(TaskContentScreenIntent.FinishEdit(it))
                    },
                    onDeleteClicked = {
                        performAction(TaskContentScreenIntent.DeleteTask)
                        dismissContent()
                    },
                    onDoneTask = {
                        performAction(TaskContentScreenIntent.DoneTask)
                        dismissContent()
                    },
                )
            }
        }
    }
}

@Composable
internal fun OnTaskContent(
    onEditing: () -> Boolean,
    onTask: () -> HomeTask,
    onEditClicked: () -> Unit,
    onDoneTask: () -> Unit,
    onSave: (HomeTask) -> Unit,
    onDeleteClicked: () -> Unit,
) {
    var task by remember {
        mutableStateOf(onTask())
    }
    var shouldShowDialog by remember {
        mutableStateOf(false)
    }
    val isEditing = onEditing()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        if (shouldShowDialog) {
            EditScoreDialog(
                onDismiss = {
                    shouldShowDialog = false
                },
                onDefine = { score ->
                    onSave(
                        task.copy(
                            point = score,
                        ),
                    )
                },
            )
        }

        SimpleTextField(
            modifier = Modifier
                .fillMaxWidth(),
            readyOnly = !isEditing,
            textStyle = MaterialTheme.typography.displayMedium,
            content = getNameFormatted(task.name),
            onChange = {
                task = task.copy(name = it)
            },
        )
        Spacer(modifier = Modifier.size(32.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "descrição:",
                style = TextStyle(
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                    fontWeight = FontWeight.Bold,
                ),
            )

            val descriptionStyle = if (isEditing) {
                MaterialTheme.typography.bodyLarge.copy(
                    background = Color.Gray.copy(alpha = 0.5f),
                )
            } else {
                MaterialTheme.typography.bodyLarge
            }
            SimpleTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = descriptionStyle,
                readyOnly = !isEditing,
                content = task.description,
                onChange = {
                    task = task.copy(description = it)
                },
            )
        }
        Spacer(modifier = Modifier.size(48.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.clickable {
                    if (isEditing) {
                        shouldShowDialog = true
                    }
                },
                text = "Pontos:",
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = task?.point.toString(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Light,
                ),
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            if (!isEditing) {
                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    onClick = onEditClicked,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ModeEditOutline,
                        contentDescription = "",
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
            }
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ),
                onClick = onDeleteClicked,
            ) {
                Icon(
                    imageVector = Icons.Filled.DeleteOutline,
                    contentDescription = "",
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            if (isEditing) {
                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    onClick = { onSave(task) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "",
                    )
                }
            }
        }

        if (!isEditing) {
            Spacer(modifier = Modifier.weight(1f))

            ElevatedButton(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = {
                    onDoneTask()
                },
            ) {
                Text(text = "Finalizar Tarefa")
            }
        }
    }
}

@Composable
private fun EditScoreDialog(
    onDefine: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    CustomDialog(onDismissDialog = onDismiss) {
        DefineNoteScoreScreen(
            modifier = Modifier
                .size(460.dp),
            onDefine = onDefine,
        )
    }
}

private fun getNameFormatted(name: String): String = name.replace("\\s".toRegex(), "\n")

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun TaskContentPreview() {
    TaskContent(
        uiState = TaskContentScreenState.IDLE.copy(
            loading = false,
            editing = false,
            onContent = HomeTask(
                id = 0,
                name = "Minha Tarefa",
                description = "Essa é uma breve descrição sobre a minha tarefinha",
                position = 0,
                point = 40,
                isAssigned = false,
            ),
        ),
        performAction = {},
        onNavigate = {},
    )
}
