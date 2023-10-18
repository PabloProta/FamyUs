package com.famy.us.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.famy.us.authentication.AuthenticationContainer
import com.famy.us.feature.registration.RegistrationScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreenContainer(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigate: (String) -> Unit,
) {
    val isMemberRegistered by remember {
        viewModel.hasUserRegistered
    }

    AuthenticationContainer {
        if (!viewModel.hasMemberInternetConnection()) {
            NoInternet()
        } else {
            if (isMemberRegistered) {
                HomeMenuScreen(onNavigate)
            } else {
                RegistrationScreen(
                    onFinishRegistration = {
                        viewModel.checkMemberRegistered()
                    },
                )
            }
        }
    }
}

@Composable
fun NoInternet() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(text = "Sem conexão com a internet impossível sincronizar!")
    }
}