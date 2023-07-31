package com.famy.us.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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
    oneTapClient: OneTapClient,
) : ViewModel() {

    /**
     * Variable used to store if the user has been logged or not.
     */
    val isLogged = mutableStateOf(false)

    /**
     * This is the firebase auth entry point for the
     * authentication screen.
     */
    val auth: FirebaseAuth = Firebase.auth

    init {
        isLogged.value = auth.currentUser != null
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
}
