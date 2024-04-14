package com.famy.us.feature.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
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
import com.famy.us.core.ui.tertiary_400
import com.famy.us.core.ui.tertiary_50

@Composable
fun RegisterPersonalInfoScreen() {
    ContainerWithTopBar(
        modifier = Modifier
            .safeContentPadding()
            .padding(24.dp)
            .fillMaxSize(),
        onClickBack = { /*TODO*/ },
    ) {
        TextInformationContainer()
        Spacer(modifier = Modifier.size(24.dp))
        UserInputsContainer()
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
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
        }
    }
}

@Composable
internal fun UserInputsContainer() {
    var memberName by remember { mutableStateOf(TextFieldValue("")) }
    var memberMail by remember { mutableStateOf(TextFieldValue("")) }
    var memberPassword by remember { mutableStateOf(TextFieldValue("")) }
    var memberConfirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    NameInput(
        value = memberName,
        onValueChange = {
            memberName = it
        },
    )
    Spacer(modifier = Modifier.size(8.dp))
    EmailInput(
        value = memberMail,
        onValueChange = {
            memberMail = it
        },
    )
    Spacer(modifier = Modifier.size(8.dp))
    PasswordInput(
        value = memberPassword,
        onValueChange = {
            memberPassword = it
        },
    )
    Spacer(modifier = Modifier.size(8.dp))
    ConfirmPasswordInput(
        value = memberConfirmPassword,
        onValueChange = {
            memberConfirmPassword = it
        },
    )
}

@Composable
internal fun TextInformationContainer() {
    Text(
        text = "Boas vindas ao cadastro",
        style = H5,
        color = tertiary_50,
    )
    Spacer(modifier = Modifier.size(4.dp))
    Text(
        text = "Antes de iniciar gostariamos de saber qual tipo de conta deseja criar",
        style = BodySmallRegular,
        color = tertiary_300,
    )
}

@Composable
internal fun NameInput(
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
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_person),
                contentDescription = null,
                tint = tertiary_400,
            )
        },
        label = "Nome Completo",
        contentColor = tertiary_400,
    )
}

@Composable
internal fun EmailInput(
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
                tint = tertiary_400,
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = "Email",
        contentColor = tertiary_400,
    )
}

@Composable
internal fun PasswordInput(
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
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_password),
                contentDescription = null,
                tint = tertiary_400,
            )
        },
        isSecretInput = true,
        label = "Senha",
        contentColor = tertiary_400,
    )
}

@Composable
internal fun ConfirmPasswordInput(
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
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_password),
                contentDescription = null,
                tint = tertiary_400,
            )
        },
        isSecretInput = true,
        label = "Confirmar Senha",
        contentColor = tertiary_400,
    )
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun RegisterPersonalInfoPreview() {
    RegisterPersonalInfoScreen()
}
