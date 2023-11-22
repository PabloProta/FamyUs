package com.famy.us.feature.note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    content: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    readyOnly: Boolean = false,
    onChange: (String) -> Unit = {},
) {
    var text: String by remember {
        mutableStateOf(content)
    }
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        modifier = modifier
            .background(Color.Transparent)
            .focusRequester(focusRequester)
            .clickable(enabled = !readyOnly) {
                // Do nothing
            },
        value = text,
        readOnly = readyOnly,
        onValueChange = { newText: String ->
            text = newText
            onChange(newText)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Text(
                text = text,
                style = textStyle,
            )
            innerTextField()
        },
    )

    LaunchedEffect(Unit) {
        if (!readyOnly) {
            focusRequester.requestFocus()
        }
    }
}
