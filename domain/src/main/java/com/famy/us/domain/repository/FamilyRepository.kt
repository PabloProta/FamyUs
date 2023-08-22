package com.famy.us.domain.repository

import com.famy.us.domain.model.AdminMember
import com.famy.us.domain.model.AuthenticationMethods
import com.famy.us.domain.model.FamilyMember
import kotlinx.coroutines.flow.Flow

/**
 * Interface for repository that will be implemented in the data module.
 * This interface is responsible to provide standard methods to the domain layer related to
 * the FamilyMember only.
 */
interface FamilyRepository {

    /**
     * Method to know if user is already logged.
     *
     * @return [Boolean] true if the user is already logged in the firebase auth, otherwise false.
     */
    fun isUserAuthenticated(): Boolean

    /**
     * Method to authenticate the user.
     *
     * @param methods the method that will be used to authenticate the user.
     * @param onSuccess when the authentication has being done successfully.
     * @param onFail when the authentication gets failed.
     */
    fun authenticateUser(methods: AuthenticationMethods, onSuccess: () -> Unit, onFail: () -> Unit)

    /**
     * Method to create a new family.
     *
     * @param familyName the name for the new family.
     * @param newMember the member that is mandatory to create a new family
     */
    fun createNewFamily(familyName: String, newMember: AdminMember)

    /**
     * Method to get the current family member registered.
     *
     * @return [Flow] of  the current [FamilyMember].
     */
    fun getCurrentMember(): Flow<FamilyMember?>
}
