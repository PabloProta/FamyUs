package com.famy.us.authentication

import android.content.Context
import android.content.IntentSender
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.famy.us.core.extensions.logD
import com.famy.us.core.extensions.logE
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = koinViewModel(),
    onFinishAuthentication: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val signContract =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
        ) { result ->
            handleClientSign(viewModel, context, result)
        }
    if (viewModel.isLogged.value) {
        onFinishAuthentication()
    } else {
        Column(modifier = Modifier) {
            Row {
                Text(text = "Insira sua conta")
            }
            Button(
                onClick = {
                    signIn(
                        viewModel.client,
                        viewModel.signRequest(),
                        onSuccess = { result ->
                            val intent = IntentSenderRequest.Builder(result.pendingIntent).build()
                            signContract.launch(intent)
                        },
                        onFailure = {
                            Toast.makeText(context, "Failed on try sign in", Toast.LENGTH_SHORT)
                                .show()
                        },
                    )
                },
            ) {
                Text(text = "Sign in")
            }
        }
    }
}

private fun authenticateWithFirebase(
    idToken: String,
    auth: FirebaseAuth,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
) {
    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(firebaseCredential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                logD { "Logged with success" }
                onSuccess()
            } else {
                logD { "Login failed" }
                onFailure()
            }
        }
}

private fun signIn(
    client: SignInClient,
    signRequest: BeginSignInRequest,
    onSuccess: (BeginSignInResult) -> Unit,
    onFailure: () -> Unit = {},
) {
    client.beginSignIn(signRequest).addOnSuccessListener { result ->
        try {
            onSuccess(result)
        } catch (e: IntentSender.SendIntentException) {
            logE { "error on try sign: ${e.message}" }
        }
    }.addOnFailureListener {
        onFailure()
        logE { "Sign Fail: ${it.localizedMessage}" }
    }
}

private fun handleClientSign(
    viewModel: AuthenticationViewModel,
    context: Context,
    result: ActivityResult,
) {
    val auth = viewModel.auth
    try {
        val credential = viewModel.client.getSignInCredentialFromIntent(result.data)
        val idToken = credential.googleIdToken
        when {
            idToken != null -> {
                authenticateWithFirebase(
                    idToken,
                    auth,
                    onSuccess = {
                        viewModel.isLogged.value = auth.currentUser != null
                    },
                    onFailure = {
                        Toast.makeText(
                            context,
                            "Failed to authenticate with firebase",
                            Toast.LENGTH_SHORT,
                        )
                            .show()
                        viewModel.isLogged.value = false
                    },
                )
            }

            else -> {
                // Shouldn't happen.
                logD { "No ID token" }
            }
        }
    } catch (e: ApiException) {
        logE { "Error while trying getting the credential : ${e.localizedMessage}" }
    }
}
