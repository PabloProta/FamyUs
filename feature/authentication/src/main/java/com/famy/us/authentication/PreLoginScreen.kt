package com.famy.us.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.authentication.navigation.AuthenticationNavigation
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.H4
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.primary_main
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_main
import com.famy.us.core.utils.navigation.Destination

@Composable
fun PreLoginScreen(
    onNavigateTo: (Destination) -> Unit,
) {
    PreLoginBackground {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
        ) {
            Surface(
                modifier = Modifier
                    .padding(top = 107.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(118.dp),
                shape = CircleShape,
                color = tertiary_main,
            ) {}
            Spacer(modifier = Modifier.size(133.dp))
            Text(
                text = "Sua família mais organizada com FamyUS",
                style = H4,
                color = tertiary_50,
            )

            Text(
                text = "Selecione a opções abaixo que mais se adequa com você",
                style = BodySmallRegular,
                color = tertiary_300,
            )
            Spacer(modifier = Modifier.size(32.dp))
            ButtonsContainer(onNavigateTo)
            CreateAccountContainer()
        }
    }
}

@Composable
internal fun CreateAccountContainer() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Não possui uma conta?",
            style = BodySmallRegular,
            color = tertiary_300,
        )
        Spacer(Modifier.size(2.dp))
        Text(
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = {},
                )
                .clipToBounds(),
            text = "Criar conta",
            style = BodySmallRegular.copy(fontWeight = FontWeight.SemiBold),
            color = primary_main,
        )
    }
}

@Composable
internal fun ButtonsContainer(
    onNavigateTo: (Destination) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onNavigateTo(AuthenticationNavigation.Login)
            },
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.size(8.dp))
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {},
        ) {
            Text(text = "Entrar com QR Code")
        }
    }
}

@Composable
internal fun PreLoginBackground(content: @Composable () -> Unit) {
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
fun PreLoginScreenPreview() {
    PreLoginScreen({})
}
