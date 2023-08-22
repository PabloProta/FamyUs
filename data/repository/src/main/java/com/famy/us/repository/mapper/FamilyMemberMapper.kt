package com.famy.us.repository.mapper

import com.famy.us.domain.model.AdminMember
import com.famy.us.domain.model.FamilyMember
import com.famy.us.domain.model.NonAdminMember
import com.famy.us.repository.model.RepositoryFamilyMember

/**
 * Class to map family members data between repository and domain module.
 *
 * @property homeTaskMapper the mapper to map the tasks.
 */
internal class FamilyMemberMapper(private val homeTaskMapper: HomeTaskMapper) {

    /**
     * Method to convert a [RepositoryFamilyMember] to [AdminMember] or [NonAdminMember].
     */
    fun toDomain(repositoryFamilyMember: RepositoryFamilyMember) =
        if (repositoryFamilyMember.isAdmin) {
            AdminMember(
                id = repositoryFamilyMember.id,
                name = repositoryFamilyMember.name,
                tasks = homeTaskMapper.toDomain(repositoryFamilyMember.tasks),
                score = repositoryFamilyMember.score,
            )
        } else {
            NonAdminMember(
                id = repositoryFamilyMember.id,
                name = repositoryFamilyMember.name,
                tasks = homeTaskMapper.toDomain(repositoryFamilyMember.tasks),
                score = repositoryFamilyMember.score,
            )
        }

    /**
     * Method to convert a any kind of [FamilyMember] to [RepositoryFamilyMember].
     */
    fun toRepository(familyMember: FamilyMember) = if (familyMember is AdminMember) {
        toRepository(familyMember)
    } else {
        toRepository(familyMember as NonAdminMember)
    }

    private fun toRepository(adminMember: AdminMember) = RepositoryFamilyMember(
        id = adminMember.id,
        name = adminMember.name,
        tasks = homeTaskMapper.toRepository(adminMember.tasks),
        score = adminMember.score,
        isAdmin = true,
    )

    private fun toRepository(nonAdminMember: NonAdminMember) = RepositoryFamilyMember(
        id = nonAdminMember.id,
        name = nonAdminMember.name,
        tasks = homeTaskMapper.toRepository(nonAdminMember.tasks),
        score = nonAdminMember.score,
        isAdmin = false,
    )
}
