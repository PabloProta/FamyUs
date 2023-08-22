package com.famy.us.repository.datasource.firebase

/**
 * Interface that serves as a bridge between the repository module and the firebase module,
 * containing methods related to the [FamilyRepository].
 */
interface FamilyFirebaseDataSource {

    /**
     * Method to know if the user is already authenticated on firebase.
     */
    fun isUserAuthenticated(): Boolean

    /**
     * Method to authenticate with google account using the firebase client.
     *
     * @param idToken the credential given by google sign.
     * @param onSuccess callback for when the authentication is done successfully.
     * @param onFail callback for when the authentication has failed.
     */
    fun authenticateWithGoogle(idToken: String, onSuccess: () -> Unit, onFail: () -> Unit)
}
