package com.famy.us.domain.usecase

import com.famy.us.core.extensions.logD
import com.famy.us.domain.model.AuthenticationMethods
import com.famy.us.domain.repository.FamilyRepository

/**
 * Class to authenticate the current member.
 */
class AuthenticateMemberUseCase(private val familyRepository: FamilyRepository) {

    operator fun invoke(method: AuthenticationMethods, onSuccess: () -> Unit, onFail: () -> Unit) =
        familyRepository.authenticateUser(method, onSuccess, onFail).also {
            logD { "Authenticate user method is ${method::class.simpleName}" }
        }
}
