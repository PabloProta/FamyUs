package com.famy.us.repository.datasource.firebase

import com.famy.us.domain.model.FamilyMember
import com.famy.us.repository.model.RepositoryFamilyMember
import kotlinx.coroutines.flow.Flow

/**
 * Interface to provide method related to the family.
 */
interface FamilyFirebaseDataSource {

    /**
     * Method to know if the current user is authenticated with his credentials at the firebase
     * auth.
     *
     * @return [Boolean] true if the user is authenticated.
     */
    fun isUserAuthenticated(): Boolean

    /**
     * Method to authenticate user using the google account method.
     *
     * @param idToken the credential token.
     * @param onSuccess callback for when the method has complete successfully.
     * @param onFail when method gets fail.
     */
    fun authenticateWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    )

    /**
     * Method to create a new family.
     *
     * @param familyName the name for the new family.
     * @param newMember the member that is mandatory to create a new family
     */
    fun createNewFamily(familyName: String, newMember: RepositoryFamilyMember)

    /**
     * Method to get the current family member registered.
     *
     * @return [Flow] of  the current [FamilyMember].
     */
    fun getCurrentMember(): Flow<RepositoryFamilyMember?>
}
