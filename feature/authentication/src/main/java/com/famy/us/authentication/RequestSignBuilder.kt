package com.famy.us.authentication

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest

/**
 * Class to build a request sign.
 *
 * @property context the context used to get the resource string.
 */
internal class RequestSignBuilder(private val context: Context) {
    operator fun invoke() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId(context.getString(R.string.web_client_id))
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false)
                .build(),
        )
        .setAutoSelectEnabled(true)
        .build()
}
