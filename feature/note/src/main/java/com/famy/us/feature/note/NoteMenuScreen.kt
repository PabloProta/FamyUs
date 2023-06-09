package com.famy.us.feature.note

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.famy.us.core.extensions.logD
import com.famy.us.core.ui.CustomDialog
import com.famy.us.domain.model.HomeTask
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NoteMenuScreen(viewModel: NoteMenuViewModel = koinViewModel()) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),
    ) {
        val tasks = state.value.listTask

        LazyColumn {
            items(tasks) { task ->
                Text(text = "task: $task")
            }
        }
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
                )
                val addingTask = state.value.addingTask
                if (state.value.shouldShowDialog) {
                    CreateHomeTaskDialog(
                        taskName = addingTask.taskName,
                        sliderPosition = addingTask.sliderPosition,
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
                            viewModel.perform(NoteScreenIntent.SaveTask(it))
                        },
                    )
                }
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
) {
    Column(
        modifier =
        Modifier.fillMaxSize(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateHomeTaskDialog(
    taskName: String,
    sliderPosition: Int,
    onDismissDialog: () -> Unit,
    onTypeText: (String) -> Unit,
    onSlideSlider: (Float) -> Unit,
    onSaveTask: (HomeTask) -> Unit,
) {
    val context = LocalContext.current

    CustomDialog(showDialog = {
        if (!it) {
            onDismissDialog()
        }
    }) {
        Text(text = "Qual vai ser a tarefa?")
        TextField(
            value = taskName,
            onValueChange = onTypeText,
        )
        Slider(
            value = sliderPosition.toFloat(),
            onValueChange = onSlideSlider,
            valueRange = 0f..100f,
        )
        Text(
            text = sliderPosition.toString(),
        )
        IconButton(
            content = {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "ds")
            },
            onClick = {
                if (taskName.isEmpty() || sliderPosition < 1) {
                    Toast.makeText(context, "Preencha os campos", Toast.LENGTH_SHORT).show()
                } else {
                    onSaveTask(HomeTask(0, taskName, sliderPosition, false))
                }
            },
        )
    }
}
