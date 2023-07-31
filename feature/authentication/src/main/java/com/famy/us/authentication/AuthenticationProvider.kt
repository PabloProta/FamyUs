package com.famy.us.authentication

import androidx.compose.runtime.Composable

/**
 * Composable to be use for every one that need check for user authentication and when
 * necessary ask for one.
 */
@Composable
fun AuthenticationContainer(content: @Composable () -> Unit) {
    AuthenticationScreen(
        onFinishAuthentication = {
            content()
        },
    )
}
