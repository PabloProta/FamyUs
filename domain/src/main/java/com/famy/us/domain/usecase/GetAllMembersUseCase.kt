package com.famy.us.domain.usecase

import com.famy.us.core.extensions.logD
import com.famy.us.domain.repository.FamilyRepository

/**
 * Use case to get all members.
 *
 * @property familyRepository the repository used to get the member by this use case.
 */
class GetAllMembersUseCase(private val familyRepository: FamilyRepository) {
    operator fun invoke() {
        logD { "GetAllMembersUseCase" }
        familyRepository.getAllMembers()
    }
}
