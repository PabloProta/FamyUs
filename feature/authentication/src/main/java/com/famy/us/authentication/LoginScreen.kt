package com.famy.us.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H6
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.components.DefaultTextField
import com.famy.us.core.ui.secondary_300
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_400
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_800
import com.famy.us.core.ui.tertiary_main

@Composable
fun LoginScreen() {
    LoginBackground {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 48.dp),
        ) {
            val logo = createRef()
            val contentContainer = createRef()
            Surface(
                modifier = Modifier
                    .constrainAs(logo) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(contentContainer.top)
                    }
                    .size(116.dp),
                shape = CircleShape,
                color = tertiary_main,
            ) {}

            Column(
                modifier = Modifier
                    .constrainAs(contentContainer) {
                        top.linkTo(logo.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Vamos fazer login?",
                    style = H6,
                    textAlign = TextAlign.Center,
                    color = tertiary_50,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Digite seu usuário e senha para entrar",
                    style = BodySmallRegular,
                    textAlign = TextAlign.Center,
                    color = tertiary_300,
                )
                Spacer(modifier = Modifier.size(14.dp))
                InputsContainer()
                LoginThirdAppsContainer()
            }
        }
    }
}

@Composable
internal fun LoginThirdAppsContainer() {
    Spacer(modifier = Modifier.size(24.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Ou faça login com",
        style = BodySmallRegular,
        color = tertiary_300,
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.size(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        LoginMethodContainer {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    modifier = Modifier
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        LoginMethodContainer {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    modifier = Modifier
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
internal fun InputsContainer() {
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    UserNameInput(
        value = userName,
        onValueChange = {
            userName = it
        },
    )
    Spacer(modifier = Modifier.size(8.dp))
    UserPasswordInput(
        value = password,
        onValueChange = {
            password = it
        },
    )
    Spacer(modifier = Modifier.size(8.dp))
    TextButton(
        onClick = { /*TODO*/ },
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Esqueceu a senha?",
            style = BodySmallRegular,
            textAlign = TextAlign.End,
            color = secondary_300,
        )
    }
    DefaultButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { /*TODO*/ },
    ) {
        Text(
            text = "Entrar",
            style = ButtonMedium,
        )
    }
}

@Composable
internal fun LoginMethodContainer(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(12.dp),
                color = tertiary_800,
            ),
        color = Color.Transparent,
    ) {
        content()
    }
}

@Composable
internal fun UserNameInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = "Usuário",
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_person),
                contentDescription = null,
                tint = tertiary_400,
            )
        },
    )
}

@Composable
internal fun UserPasswordInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    var shouldShowPassword by remember { mutableStateOf(false) }
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = "Senha",
        isSecretInput = !shouldShowPassword,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_password),
                contentDescription = null,
                tint = tertiary_400,
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { shouldShowPassword = !shouldShowPassword },
            ) {
                if (shouldShowPassword) {
                    Icon(
                        Icons.Outlined.VisibilityOff,
                        contentDescription = null,
                        tint = tertiary_400,
                    )
                } else {
                    Icon(
                        Icons.Outlined.Visibility,
                        contentDescription = null,
                        tint = tertiary_400,
                    )
                }
            }
        },
    )
}

@Composable
internal fun LoginBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background_pre_login),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        content()
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
