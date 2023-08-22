package com.famy.us.domain.usecase

import com.famy.us.core.extensions.logD
import com.famy.us.domain.repository.FamilyRepository

/**
 * Use case to get if the current user is authenticated on firebase.
 *
 * @property familyRepository the repository used by this use case.
 */
class IsUserLoggedUseCase(private val familyRepository: FamilyRepository) {

    operator fun invoke(): Boolean = familyRepository.isUserAuthenticated().also {
        logD { "IsUserLoggedUseCase: $it" }
    }
}
