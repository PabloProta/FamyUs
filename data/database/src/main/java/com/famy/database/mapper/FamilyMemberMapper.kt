package com.famy.database.mapper

import com.famy.us.repository.model.AdminMember
import com.famy.us.repository.model.NonAdminMember
import com.famy.database.model.FamilyMember as DatabaseFamilyMember
import com.famy.us.repository.model.FamilyMember as RepositoryFamilyMember

/**
 * Mapper class to convert models between data:database and data:repository module.
 *
 * @property homeTaskMapper is the mapper to the tasks of family members.
 */
internal class FamilyMemberMapper(private val homeTaskMapper: HomeTaskMapper) {

    /**
     * Method to convert a database family member into a repository family member data model.
     *
     * @param databaseFamilyMember the model that will be converted.
     * @return a new model converted to the repository module.
     */
    fun toRepository(databaseFamilyMember: DatabaseFamilyMember): RepositoryFamilyMember =
        databaseFamilyMember.run {
            if (isAdmin) {
                AdminMember(
                    id,
                    name,
                    homeTaskMapper.toRepository(tasks),
                    score,
                )
            } else {
                NonAdminMember(
                    id,
                    name,
                    homeTaskMapper.toRepository(tasks),
                    score,
                )
            }
        }

    /**
     * Method to convert a repository family member into a database family member data model.
     *
     * @param repositoryFamilyMember the model that will be converted.
     * @return a new model converted to the database module.
     */
    fun toDatabase(repositoryFamilyMember: RepositoryFamilyMember): DatabaseFamilyMember =
        if (repositoryFamilyMember is AdminMember) {
            repositoryFamilyMember.run {
                DatabaseFamilyMember(
                    id,
                    name,
                    homeTaskMapper.toDatabase(tasks),
                    score,
                    isAdmin = true,
                )
            }
        } else {
            repositoryFamilyMember as NonAdminMember
            repositoryFamilyMember.run {
                DatabaseFamilyMember(
                    id,
                    name,
                    homeTaskMapper.toDatabase(tasks),
                    score,
                    isAdmin = false,
                )
            }
        }
}
