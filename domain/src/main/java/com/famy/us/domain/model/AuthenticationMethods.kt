package com.famy.us.domain.model

/**
 * Sealed Interface representing all authentication kinds that is possible in this app.
 */
sealed interface AuthenticationMethods {
    data class Google(val idToken: String) : AuthenticationMethods
    object None : AuthenticationMethods
}
