package com.famy.us.data.firebase

import com.famy.us.core.extensions.logD
import com.famy.us.repository.datasource.firebase.FamilyFirebaseDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Class that implements the respective data source.
 */
internal class FamilyFirebaseDataSourceImpl : FamilyFirebaseDataSource {

    /**
     * This is the firebase auth entry point for the
     * authentication screen.
     */
    private val auth: FirebaseAuth = Firebase.auth
    override fun isUserAuthenticated(): Boolean = auth.currentUser != null

    override fun authenticateWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    ) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    logD { "Logged with success" }
                    onSuccess()
                } else {
                    logD { "Login failed" }
                    onFail()
                }
            }
    }
}
