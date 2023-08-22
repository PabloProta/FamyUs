package com.famy.us.data.firebase.mapper

import com.famy.us.data.firebase.model.FirebaseMember
import com.famy.us.repository.model.RepositoryFamilyMember

/**
 * Mapper between firebase and repository.
 *
 */
internal class FamilyMemberMapper {

    /**
     * Method to convert a repository family membre to a firebase member.
     */
    fun toFirebase(repositoryFamilyMember: RepositoryFamilyMember) = FirebaseMember(
        id = repositoryFamilyMember.id,
        name = repositoryFamilyMember.name,
        tasks = repositoryFamilyMember.tasks.map {
            it.id
        },
        score = repositoryFamilyMember.score,
        isAdmin = repositoryFamilyMember.isAdmin,
    )

    /**
     * TODO - Need find a way to retrieve the tasks correctly.
     *
     * Method to convert a firebase member to a repository family member.
     */
    fun toRepository(firebaseMember: FirebaseMember) = RepositoryFamilyMember(
        id = firebaseMember.id ?: 0,
        name = firebaseMember.name ?: "",
        tasks = emptyList(),
        isAdmin = firebaseMember.isAdmin ?: false,
        score = firebaseMember.score ?: 0,
    )
}
