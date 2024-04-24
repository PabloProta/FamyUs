package com.famy.us.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.famy.us.authentication.AuthenticationContainer
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreenContainer(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val isMemberRegistered by remember {
        viewModel.hasUserRegistered
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        AuthenticationContainer {
            if (!viewModel.hasMemberInternetConnection()) {
                NoInternet()
            } else {
                if (isMemberRegistered) {
                    HomeMenuScreenContainer {}
                } else {
                    // if member is Registered
                }
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
