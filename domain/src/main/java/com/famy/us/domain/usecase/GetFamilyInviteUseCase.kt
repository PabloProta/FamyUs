package com.famy.us.domain.usecase

import com.famy.us.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to get a family invite data.
 *
 * @property repository used to get the family invite.
 */
class GetFamilyInviteUseCase(private val repository: FamilyRepository) {
    operator fun invoke(): Flow<String> = repository.getFamilyInvite()
}
