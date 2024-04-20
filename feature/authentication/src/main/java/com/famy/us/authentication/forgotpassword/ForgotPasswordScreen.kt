package com.famy.us.authentication.forgotpassword

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
fun ForgotPasswordScreen(
    popBackStack: () -> Unit,
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    BackHandler {
        popBackStack()
    }
    ContainerWithTopBar(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        onClickBack = popBackStack,
    ) {
        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.size(32.dp))
            TextsContainer()
            Spacer(modifier = Modifier.size(24.dp))
            EmailInput(
                value = email,
                onValueChange = {
                    email = it
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
                    color = Color.White,
                    style = ButtonMedium,
                )
            }
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
private fun EmailInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_mail),
                contentDescription = null,
            )
        },
        label = "Email",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
    )
}

@Composable
private fun TextsContainer() {
    Text(
        text = "Esqueceu a senha?",
        color = tertiary_50,
        style = H5,
    )
    Spacer(modifier = Modifier.size(4.dp))
    Text(
        text = "Digite seu email de cadastro, enviaremos para você um código de confirmação",
        color = tertiary_300,
        style = BodySmallRegular,
    )
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun ForgotPasswordPreview() {
    ForgotPasswordScreen({})
}
