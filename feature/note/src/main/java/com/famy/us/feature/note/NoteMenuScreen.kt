package com.famy.us.feature.note

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.famy.us.core.extensions.logD
import com.famy.us.core.ui.CustomDialog
import com.famy.us.domain.model.HomeTask

@Composable
internal fun NoteMenuScreen(viewModel: NoteMenuViewModel) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),

        ) {

        when (state.value) {
            NoteScreenState.LoadingTasks -> {
                logD { "LoadingTasks" }
                ShowProgress()
            }
            is NoteScreenState.NoteScreenMenu -> {
                logD { "NoteMenu" }
                onLoadItem(viewModel = viewModel, state.value as NoteScreenState.NoteScreenMenu)
            }
            NoteScreenState.AddingTask -> {
                logD { "OpenDialog" }
                CreateHomeTaskDialog(viewModel)
            }
            else -> {}
        }
    }
}

@Composable
fun ShowProgress() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            strokeWidth = 2.dp
        )
    }
}

@Composable
internal fun onLoadItem(viewModel: NoteMenuViewModel, value: NoteScreenState.NoteScreenMenu) {
    val tasks = value.listTask

    LazyColumn {
        items(tasks) { task ->
            Text(text = "task: $task")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, bottom = 32.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            FloatingActionButton(
                modifier = Modifier,
                onClick = {
                    viewModel.perform(NoteScreenIntent.AddTask)
                },
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateHomeTaskDialog(viewModel: NoteMenuViewModel) {
    var sliderValue by remember {
        mutableStateOf(0f)
    }
    var taskName by remember {
        mutableStateOf("")
    }
    CustomDialog(showDialog = {
        if (!it) {
            viewModel.perform(NoteScreenIntent.Back)
        }
    }) {
        Text(text = "Qual vai ser a tarefa?")
        TextField(
            value = taskName,
            onValueChange = {
                taskName = it
            },
        )
        Slider(
            value = sliderValue,
            onValueChange = { _sliderValue ->
                sliderValue = _sliderValue
            },
            valueRange = 0f..100f,
        )
        Text(
            text = "${sliderValue.toInt()}",
        )
        IconButton(
            content = {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "ds")
            },
            onClick = {
                viewModel.perform(
                    NoteScreenIntent.SaveTask(
                        HomeTask(0, "acah", 300, false)
                    )
                )
            },
        )
    }
}
