package com.famy.us.domain.usecase

import com.famy.us.core.extensions.logD
import com.famy.us.domain.repository.FamilyRepository

/**
 * Use case to know if the user is already registered at the cloud side.
 *
 * @property familyRepository the repository used by this use case.
 */
class IsUserRegisteredUseCase(private val familyRepository: FamilyRepository) {

    /**
     * Method that invokes this use case.
     *
     * @return [Boolean] true if the user is already registered.
     */
    operator fun invoke(): Boolean = familyRepository.isUserAlreadyRegistered().also {
        logD { "IsUserRegisteredUseCase : $it " }
    }
}
