package com.famy.us.feature.note.createNote.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CreateNoteDescriptionScreen(onCreateDescription: (String) -> Unit) {
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 32.dp),
            text = "Se quiser de uma breve descrição pra ela:",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(24.dp))
        SimpleTextField(
            onDone = {
                name = it
            },
            onChange = {
                name = it
            }
        )
        Spacer(modifier = Modifier.size(24.dp))
        ElevatedButton(
            modifier = Modifier
                .align(Alignment.End),
            onClick = {
                onCreateDescription(name)
            },
        ) {
            if (name.isEmpty()) {
                Text(text = "Pular")
            } else {
                Text(text = "Salvar")
            }
        }
    }
}

@Composable
fun SimpleTextField(
    onDone: (String) -> Unit,
    onChange: (String) -> Unit,
) {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = Modifier
            .background(Color.Transparent)
            .focusRequester(focusRequester),
        value = text,
        onValueChange = { newText ->
            text = newText
            onChange(newText.text)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone(text.text)
            },
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            background = Color.Gray.copy(alpha = 0.5f),
        )
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun CreateNoteDescriptionPreview() {
    CreateNoteDescriptionScreen(onCreateDescription = {})
}
