package com.famy.us.feature.note.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.CustomDialog
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.ShowDialog

@Suppress("LongMethod")
@Composable
internal fun HomeTaskDialog(
    taskProvider: () -> HomeTask,
    showDialogProvider: () -> ShowDialog,
    onDismissDialog: () -> Unit,
    onEditClicked: () -> Unit,
    onSaveClicked: (HomeTask) -> Unit,
) {
    val showDialog = showDialogProvider()
    if (!showDialog.shouldShowDialog) return
    val task = taskProvider()
    var score by remember {
        mutableStateOf(task.point.toFloat())
    }
    var name by remember {
        mutableStateOf(task.name)
    }
    CustomDialog(onDismissDialog = onDismissDialog) {
        if (!showDialog.isEditingTask) {
            AllowEdit(taskName = name, onEditClicked = onEditClicked)
        }
        Spacer(
            modifier = Modifier
                .size(height = 10.dp, width = 0.dp),
        )
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
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
                onDone = {
                    onSaveClicked(
                        task.copy(
                            name = name,
                            point = score.toInt(),
                        ),
                    )
                },
            ),
        )
        ShowScore(
            isEditing = showDialog.isEditingTask,
            scoreProvider = { score },
            onScoreChange = {
                score = it
            },
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
                        onSaveClicked(
                            task.copy(
                                name = name,
                                point = score.toInt(),
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun AllowEdit(
    taskName: String,
    onEditClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
            modifier = Modifier,
            onClick = onEditClicked,
        ) {
            Icon(
                imageVector = Icons.Sharp.Edit,
                contentDescription = "edit $taskName",
            )
        }
    }
}

@Composable
fun ShowScore(
    isEditing: Boolean,
    scoreProvider: () -> Float,
    onScoreChange: (Float) -> Unit,
) {
    val score = scoreProvider()
    Slider(
        value = score,
        onValueChange = onScoreChange,
        valueRange = 0f..100f,
        enabled = isEditing,
    )
    Text(
        text = score.toInt().toString(),
    )
}
