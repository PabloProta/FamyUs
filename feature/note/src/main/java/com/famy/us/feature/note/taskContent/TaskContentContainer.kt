package com.famy.us.feature.note.taskContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ModeEditOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.notescreen.ShowProgress
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskContentProvider(taskId: Int) {
    TaskContentContainer(taskId = taskId)
}

@Composable
internal fun TaskContentContainer(
    viewModel: TaskContentViewModel = koinViewModel(),
    taskId: Int,
) {
    val uiState by remember {
        viewModel.uiState
    }
    if (uiState.onContent == null) {
        viewModel.perform(TaskContentScreenIntent.LoadTask(taskId))
    }
    TaskContent(
        uiState = uiState,
        performAction = {
            viewModel.perform(it)
        },
    )
}

@Composable
private fun TaskContent(
    uiState: TaskContentScreenState,
    performAction: (TaskContentScreenIntent) -> Unit,
) {
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
                        performAction(TaskContentScreenIntent.FinishEdit)
                    },
                    onDeleteClicked = {
                        performAction(TaskContentScreenIntent.DeleteTask)
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
    onSave: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    val task = onTask()
    val isEditing = onEditing()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
    ) {
        Text(
            modifier = Modifier
                .widthIn(40.dp, 160.dp),
            text = task.name,
            style = MaterialTheme.typography.displayMedium,
        )
        Spacer(modifier = Modifier.size(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            fontWeight = FontWeight.Bold,
                        ),
                    ) {
                        append("descrição:")
                    }
                    withStyle(
                        SpanStyle(
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            fontWeight = FontWeight.Light,
                        ),
                    ) {
                        append(task.description)
                    }
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
        Spacer(modifier = Modifier.size(80.dp))
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.End,
        ) {
            if (!isEditing) {
                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
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
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
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
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "",
                    )
                }
            }
        }
    }
}

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
                point = 40,
                isAssigned = false,
            ),
        ),
        performAction = {},
    )
}
