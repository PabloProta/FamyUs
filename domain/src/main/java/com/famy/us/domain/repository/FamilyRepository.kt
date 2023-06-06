package com.famy.us.domain.repository

import com.famy.us.domain.model.FamilyMember

/**
 * Interface for repository that will be implemented in the data module.
 * This interface is responsible to provide standard methods to the domain layer related to
 * the FamilyMember only.
 */
interface FamilyRepository {

    /**
     * Method to get all members registered.
     */
    fun getAllMembers(): List<FamilyMember>

    /**
     * Method to get a member by Id.
     */
    fun getMemberById(id: Int): FamilyMember

    /**
     * Method to save a member.
     *
     * @param familyMember the new member that are going to be saved.
     */
    fun saveMember(familyMember: FamilyMember)

    /**
     * Method to update a already existent member.
     *
     * @param familyMember the member that will be updated.
     */
    fun updateMember(familyMember: FamilyMember)

    /**
     * Method to delete a member by id.
     *
     * @param id the member id that are going to be deleted.
     */
    fun deleteMemberById(id: Int)
}
