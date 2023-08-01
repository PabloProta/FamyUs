package com.famy.us.authentication

import android.content.Context
import android.content.IntentSender
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.famy.us.authentication.components.SignInButton
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
    var isLoading by remember {
        mutableStateOf(false)
    }
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
        SignInScreen(
            isLoadingProvider = { isLoading },
            onCLickSign = {
                isLoading = true
                signIn(
                    viewModel.client,
                    viewModel.signRequest(),
                    onSuccess = { result ->
                        val intent = IntentSenderRequest.Builder(result.pendingIntent).build()
                        signContract.launch(intent)
                    },
                    onFailure = {
                        isLoading = false
                        Toast.makeText(context, "Failed on try sign in", Toast.LENGTH_SHORT)
                            .show()
                    },
                )
            },
        )
    }
}

@Composable
internal fun SignInScreen(
    onCLickSign: () -> Unit,
    isLoadingProvider: () -> Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(210.dp))
        Text(
            text = "bem vindo a",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraLight,
            ),
        )
        Text(
            text = "Nossa\nFamilia",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Seu app de tarefas compartilhadas",
            style = MaterialTheme.typography.titleSmall,
        )
        Spacer(modifier = Modifier.size(93.dp))
        SignInButton(
            isLoadingProvider = isLoadingProvider,
        ) {
            onCLickSign()
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
