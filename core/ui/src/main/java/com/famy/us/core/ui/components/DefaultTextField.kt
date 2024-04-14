package com.famy.us.core.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.tertiary_100
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_600

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    isSecretInput: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    label: String = "",
    contentColor: Color = tertiary_600,
    keyboardOptions: KeyboardOptions = if (isSecretInput) {
        KeyboardOptions(keyboardType = KeyboardType.Password)
    } else {
        KeyboardOptions.Default
    },
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    TextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = tertiary_300,
                shape = RoundedCornerShape(12.dp),
            ),
        value = value,
        onValueChange = onValueChange,
        textStyle = BodySmallRegular.copy(color = contentColor),
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = {
            Text(
                text = label,
                style = BodySmallRegular,
                color = contentColor,
            )
        },
        visualTransformation = getVisualTransformation(isSecretInput),
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = tertiary_100,
            unfocusedContainerColor = tertiary_100,
        ),
    )
}

private fun getVisualTransformation(isSecret: Boolean): VisualTransformation = if (isSecret) {
    PasswordVisualTransformation()
} else {
    VisualTransformation.None
}

@Preview
@Composable
fun DefaultTextFieldPreview() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    DefaultTextField(
        value = text,
        label = "Usuário",
        leadingIcon = {
            Icon(Icons.Outlined.Mail, contentDescription = null)
        },
        trailingIcon = {
            Icon(Icons.Outlined.Visibility, contentDescription = null)
        },
        onValueChange = {
            text = it
        },
    )
}
