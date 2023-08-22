package com.famy.us.feature.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.feature.registration.components.RoleCard

@Composable
fun RegistrationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "O que você veio fazer aqui?",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .size(40.dp),
        )

        RoleCard(
            roleTitle = "Criar uma familia",
            roleDescription = "Ainda não tem uma familia criada para compartilhar tarefas? só clicar aqui!",
            onClickIt = {},
        )
        Spacer(
            modifier = Modifier
                .size(24.dp),
        )
        RoleCard(
            roleTitle = "Vincular a uma familia",
            roleDescription = "Alguém ja registrou a família pra você e veio aqui só vincular, chega mais, clica ai!",
            onClickIt = {
            },
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    device = "id:pixel_xl",
)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen()
}
