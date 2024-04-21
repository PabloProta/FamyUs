package com.famy.us.authentication.forgotpassword

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.authentication.navigation.AuthenticationNavigation
import com.famy.us.core.extensions.logE
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.components.ContainerWithTopBar
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.tertiary_100
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_900
import com.famy.us.core.utils.navigation.Destination

@Composable
fun InsertEmailCodeScreen(
    email: String,
    popBackStack: () -> Unit,
    onNavigateAt: (Destination) -> Unit,
) {
    BackHandler {
        popBackStack()
    }
    ContainerWithTopBar(
        onClickBack = popBackStack,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .safeDrawingPadding(),
        ) {
            Spacer(modifier = Modifier.size(38.dp))
            TextsContainer(email = email)
            Spacer(modifier = Modifier.size(24.dp))
            CodeInput(
                onValueChange = {
                    // Need to check the code input
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onNavigateAt(AuthenticationNavigation.InsertNewPassword)
                },
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
private fun CodeInput(
    onValueChange: (String) -> Unit,
) {
    var codeString: List<Char> by remember { mutableStateOf(listOf('-', '-', '-', '-', '-')) }
    val focusManager = LocalFocusManager.current
    LazyRow(
        modifier = Modifier
            .focusGroup()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        itemsIndexed(
            items = codeString,
        ) { index, input ->
            CodeInputItem(
                value = input,
                onValueChange = {
                    if (it != '-') {
                        val newList = codeString.mapIndexed { i, inputItem ->
                            if (index == i) {
                                it
                            } else {
                                inputItem
                            }
                        }
                        codeString = newList
                        focusManager.moveFocus(FocusDirection.Next)
                        onValueChange(codeString.toString())
                    }
                },
            )
        }
    }
}

@Suppress("SwallowedException")
@Composable
private fun CodeInputItem(
    value: Char,
    onValueChange: (Char) -> Unit,
) {
    Spacer(modifier = Modifier.size(8.dp))
    Column(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(12.dp),
                color = tertiary_100,
            )
            .size(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BasicTextField(
            value = "$value",
            onValueChange = {
                if (it.isEmpty()) {
                    onValueChange('-')
                } else {
                    try {
                        if (it[1] == '-') {
                            onValueChange(it[0])
                        } else {
                            onValueChange(it[1])
                        }
                    } catch (e: StringIndexOutOfBoundsException) {
                        onValueChange(it[0])
                    }
                }
            },
            textStyle = BodySmallRegular.copy(
                color = tertiary_900,
                textAlign = TextAlign.Center,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
}

private const val EmailTake = 3

@Composable
private fun TextsContainer(
    email: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = "Código enviado",
        color = tertiary_50,
        style = H5,
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.size(4.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = "Código enviado para o email ${email.take(EmailTake)}***" +
            "${extractEmailProvider(email)} " +
            "um código de confirmação.  Verifique a caixa principal e spam",
        color = tertiary_300,
        style = BodySmallRegular,
        textAlign = TextAlign.Center,
    )
}

private fun extractEmailProvider(email: String): String {
    val atIndex = email.lastIndexOf('@')
    return if (atIndex != -1) {
        email.substring(atIndex)
    } else {
        logE { "Invalid email format" }
        ""
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun InsertEmailCodePreview() {
    InsertEmailCodeScreen("pabloprota@gmail.com", {}, {})
}
