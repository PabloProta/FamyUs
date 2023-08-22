package com.famy.us.domain.usecase

import com.famy.us.core.extensions.logD
import com.famy.us.domain.repository.FamilyRepository

/**
 * Use case to set that the current app user finished the registration flow.
 *
 * @property familyRepository the repository used by this use case.
 */
class SetUserRegisteredUseCase(private val familyRepository: FamilyRepository) {

    /**
     * Method that invokes this use case.
     */
    operator fun invoke() = familyRepository.setIsUserRegistered().also {
        logD { "SetUserRegisteredUseCase" }
    }
}
