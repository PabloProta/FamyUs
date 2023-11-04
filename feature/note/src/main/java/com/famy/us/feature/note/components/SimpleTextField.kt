package com.famy.us.feature.note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    content: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    prefix: @Composable () -> Unit = {},
    readyOnly: Boolean = false,
    onChange: (String) -> Unit = {},
) {
    var text by remember {
        mutableStateOf(TextFieldValue(content))
    }
    val focusRequester = remember { FocusRequester() }
    TextField(
        modifier = modifier
            .background(Color.Transparent)
            .focusRequester(focusRequester)
            .clickable(enabled = !readyOnly) {
                // Do nothing
            },
        value = text,
        prefix = prefix,
        readOnly = readyOnly,
        onValueChange = { newText ->
            text = newText
            onChange(newText.text)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = textStyle,
    )

    LaunchedEffect(Unit) {
        if (!readyOnly) {
            focusRequester.requestFocus()
        }
    }
}
