package com.famy.us.domain.usecase

import com.famy.us.domain.model.FamilyMember
import com.famy.us.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to get the current member registered in the firebase database.
 *
 * @property repository the repository used to get the current user.
 */
class GetCurrentMemberUseCase(private val repository: FamilyRepository) {

    /**
     * Method to invoke this use case.
     *
     * @return a [Flow] of the current [FamilyMember] registered in the device.
     */
    operator fun invoke(): Flow<FamilyMember?> = repository.getCurrentMember()
}
