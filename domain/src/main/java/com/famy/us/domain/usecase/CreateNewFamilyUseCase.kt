package com.famy.us.domain.usecase

import com.famy.us.core.extensions.logD
import com.famy.us.domain.model.AdminMember
import com.famy.us.domain.repository.FamilyRepository

/**
 * Use case to create a new family.
 *
 * @property repository the repository used to create the user and the family.
 */
class CreateNewFamilyUseCase(private val repository: FamilyRepository) {

    /**
     * Method that invokes this use case to create a new family for the family member.
     *
     * @param familyName the family name.
     * @param member the member that will be first registered.
     */
    operator fun invoke(familyName: String, member: AdminMember) =
        repository.createNewFamily(familyName, member).also {
            logD { "CreateNewFamilyUseCase | name: $familyName" }
        }
}
