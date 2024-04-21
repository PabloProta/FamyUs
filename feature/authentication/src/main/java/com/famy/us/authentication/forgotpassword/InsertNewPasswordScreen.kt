package com.famy.us.authentication.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.components.ContainerWithTopBar
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.components.DefaultTextField
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_50

@Composable
fun InsertNewPasswordScreen(
    popBackStack: () -> Unit,
) {
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    ContainerWithTopBar(
        onClickBack = popBackStack,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.size(36.dp))
            TextsContainer()
            Spacer(modifier = Modifier.size(24.dp))
            PasswordInput(
                value = password,
                onValueChange = {
                    password = it
                },
            )
            Spacer(modifier = Modifier.size(24.dp))
            ConfirmPasswordInput(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
            ) {
                Text(
                    text = "Avançar",
                    style = ButtonMedium,
                    color = Color.White,
                )
            }
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
private fun DefaultPasswordInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
) {
    var showPassword by remember { mutableStateOf(false) }
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        isSecretInput = !showPassword,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_password),
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    showPassword = !showPassword
                },
            ) {
                val icon = if (!showPassword) {
                    Icons.Outlined.Visibility
                } else {
                    Icons.Outlined.VisibilityOff
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                )
            }
        },
        label = label,
    )
}

@Composable
private fun PasswordInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    DefaultPasswordInput(value = value, onValueChange = onValueChange, label = "Senha")
}

@Composable
private fun ConfirmPasswordInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    DefaultPasswordInput(value = value, onValueChange = onValueChange, label = "Confirmar Senha")
}

@Composable
private fun TextsContainer() {
    Text(
        text = "Digite uma nova senha",
        style = H5,
        color = tertiary_50,
    )
    Text(
        text = "Insira abaixo sua nova senha",
        style = BodySmallRegular,
        color = tertiary_300,
    )
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun InsertNewPasswordPreview() {
    InsertNewPasswordScreen({})
}
