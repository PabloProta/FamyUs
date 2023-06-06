package com.famy.us.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.CustomDialog

@Composable
fun NoteMenuScreen() {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        CreateHomeTaskDialog(shouldShow = {
            showDialog.value = it
        })
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
                onClick = {
                    showDialog.value = true
                },
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHomeTaskDialog(shouldShow: (Boolean) -> Unit) {
    var sliderValue by remember {
        mutableStateOf(0f)
    }
    var taskName by remember {
        mutableStateOf("")
    }
    CustomDialog(showDialog = shouldShow) {
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
                shouldShow(false)
            },
        )
    }
}
