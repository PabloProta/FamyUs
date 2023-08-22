package com.famy.us.feature.registration

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.feature.registration.components.RoleCard
import com.famy.us.feature.registration.machine.RegistrationScreenIntent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    onFinishRegistration: () -> Unit,
    viewModel: RegistrationViewModel = koinViewModel(),
) {
    val uiState by remember {
        viewModel.uiState
    }

    BackHandler {
        viewModel.perform(RegistrationScreenIntent.Back)
    }

    if (uiState.registrationFinished) {
        onFinishRegistration()
    }

    ShowProgress(
        isLoadingProvider = {
            !uiState.showingContent
        },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.size(104.dp))

        when {
            uiState.creatingMember != null -> CreatingMemberScreen(
                onSaveMember = {
                    viewModel.perform(
                        RegistrationScreenIntent.SaveMember(it),
                    )
                },
            )

            uiState.creatingFamily != null -> CreateFamilyScreen(
                onSaveFamily = {
                    viewModel.perform(RegistrationScreenIntent.SaveFamily(it))
                },
            )

            uiState.showingContent -> RegistrationRoute(
                onClickCreateFamily = {
                    viewModel.perform(RegistrationScreenIntent.CreateFamily)
                },
                onClickSyncWithFamily = {},
            )
        }
    }
}

@Composable
fun ShowProgress(isLoadingProvider: () -> Boolean) {
    if (isLoadingProvider()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                strokeWidth = 2.dp,
            )
        }
    }
}

@Composable
fun RegistrationRoute(
    onClickCreateFamily: () -> Unit,
    onClickSyncWithFamily: () -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "O que você veio fazer aqui?",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .size(80.dp),
        )

        RoleCard(
            roleTitle = "Criar uma familia",
            roleDescription = "Ainda não tem uma familia criada para compartilhar tarefas? só clicar aqui!",
            onClickIt = onClickCreateFamily,
        )
        Spacer(
            modifier = Modifier
                .size(24.dp),
        )
        RoleCard(
            roleTitle = "Vincular a uma familia",
            roleDescription = "Alguém ja registrou a família pra você e veio aqui só vincular, chega mais, clica ai!",
            onClickIt = onClickSyncWithFamily,
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
    RegistrationScreen({})
}
