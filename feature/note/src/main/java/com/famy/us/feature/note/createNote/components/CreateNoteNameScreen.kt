package com.famy.us.feature.note.createNote.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CreateNoteNameScreen(onCreateName: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 40.dp),
    ) {
        var name by remember {
            mutableStateOf("")
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Qual o Nome da Tarefa?",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = name,
            onValueChange = {
                name = it
            },
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
                    onCreateName(name)
                },
            ),
        )
        Spacer(modifier = Modifier.size(24.dp))
        ElevatedButton(
            modifier = Modifier
                .align(Alignment.End),
            onClick = {
                onCreateName(name)
            },
        ) {
            Text(text = "Salvar")
        }
    }
}


@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun CreateNoteNameScreenPreview() {
    CreateNoteNameScreen({})
}
