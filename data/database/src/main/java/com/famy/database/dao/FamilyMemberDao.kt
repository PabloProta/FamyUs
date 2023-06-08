package com.famy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.famy.database.model.FamilyMember
import kotlinx.coroutines.flow.Flow

/**
 * Interface responsible to provide the data access to the objects related to the family member.
 */
@Dao
interface FamilyMemberDao {

    /**
     * Method to get all members from database
     */
    @Query("SELECT * FROM family_member")
    fun getAllMembers(): Flow<List<FamilyMember>>

    /**
     * Method to get a member by id.
     */
    @Query("SELECT * FROM family_member WHERE id = (:memberId)")
    fun getMemberById(memberId: Int): Flow<FamilyMember>

    /**
     * Method to save a member using a [FamilyMember] object.
     */
    @Insert
    fun saveMember(familyMember: FamilyMember)

    /**
     * Method to update a member.
     */
    @Update
    fun updateMember(familyMember: FamilyMember)

    /**
     * Method to delete a member by id.
     */
    @Query("DELETE FROM family_member WHERE id = (:memberId)")
    fun deleteMemberById(memberId: Int)
}
