package com.famy.us.authentication

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity

/**
 * Class to give the oneTap client from google services, to get the credentials to authenticate
 * with firebase auth.
 *
 * @property context context used by the client.
 */
internal class OneTapClient(private val context: Context) {

    /**
     * Method to invoke this class.
     */
    operator fun invoke() = Identity.getSignInClient(context)
}
