package com.famy.us.domain.repository

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
     * Method to get all members registered.
     */
    suspend fun getAllMembers(): Flow<List<FamilyMember>>

    /**
     * Method to get a member by Id.
     */
    suspend fun getMemberById(id: Int): Flow<FamilyMember>

    /**
     * Method to save a member.
     *
     * @param familyMember the new member that are going to be saved.
     */
    suspend fun saveMember(familyMember: FamilyMember)

    /**
     * Method to update a already existent member.
     *
     * @param familyMember the member that will be updated.
     */
    suspend fun updateMember(familyMember: FamilyMember)

    /**
     * Method to delete a member by id.
     *
     * @param id the member id that are going to be deleted.
     */
    suspend fun deleteMemberById(id: Int)

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
     * Method to know if the user is already registered, this step is different from authentication
     * is more related to the user registration on app, with useful information to handle the
     * family tasks.
     *
     * @return [Boolean] true is the user is already registered.
     */
    fun isUserAlreadyRegistered(): Boolean

    /**
     * Should be called when the user finish the registration step.
     */
    fun setIsUserRegistered()
}
