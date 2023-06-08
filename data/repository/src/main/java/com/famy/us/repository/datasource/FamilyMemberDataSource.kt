package com.famy.us.repository.datasource

import com.famy.us.repository.model.FamilyMember
import kotlinx.coroutines.flow.Flow

/**
 * Interface responsible to provide methods to get from data source information and update them
 * about Family member.
 */
interface FamilyMemberDataSource {

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
}
