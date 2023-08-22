package com.famy.us.authentication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.famy.us.authentication.state.AuthenticationState
import com.famy.us.domain.model.AuthenticationMethods
import com.famy.us.domain.usecase.AuthenticateMemberUseCase
import com.famy.us.domain.usecase.IsUserLoggedUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * ViewModel used to the authentication screen.
 *
 * @property requestSignBuilder the requestSign builder already built.
 * @param oneTapClient the client to retrieve the current credentials of the current user
 * or ask for some new if it doesn't has.
 */
internal class AuthenticationViewModel(
    private val requestSignBuilder: RequestSignBuilder,
    private val authenticateMemberUseCase: AuthenticateMemberUseCase,
    isUserLoggedUseCase: IsUserLoggedUseCase,
    oneTapClient: OneTapClient,
) : ViewModel() {

    /**
     * Variable used to store if the user has been logged or not.
     */
    val authenticationState: MutableState<AuthenticationState> =
        mutableStateOf(AuthenticationState.IDLE)

    /**
     * This is the firebase auth entry point for the
     * authentication screen.
     */
    val auth: FirebaseAuth = Firebase.auth

    init {
        if (isUserLoggedUseCase()) {
            authenticationState.value = AuthenticationState.USER_LOGGED
        } else {
            authenticationState.value = AuthenticationState.IDLE
        }
    }

    /**
     * The client to call the google service one tap.
     */
    val client = oneTapClient()

    /**
     * Method used to get the sign builder already configured.
     * This builder is used during the sign request to apply some
     * rules.
     */
    fun signRequest() = requestSignBuilder()

    /**
     * Method to authenticate the user.
     */
    fun authenticateMember(
        method: AuthenticationMethods,
    ) {
        authenticateMemberUseCase(
            method,
            onSuccess = {
                authenticationState.value = AuthenticationState.USER_LOGGED
            },
            onFail = {
                authenticationState.value = AuthenticationState.AUTHENTICATION_FAIL
            },
        )
    }
}
