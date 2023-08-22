package com.famy.us.feature.registration.machine

import com.famy.us.core.utils.UiSate
import com.famy.us.domain.model.FamilyMember

/**
 * Data class to represent the screen state for RegistrationScreen.
 *
 * @property creatingMember state for when a member is being created.
 * @property creatingFamily for when a family is being created.
 * @property syncingFamily for when an user is being synced with a existent family.
 */
data class RegistrationScreenState(
    val showingContent: Boolean,
    val creatingMember: FamilyMember?,
    val creatingFamily: String?,
    val syncingFamily: String?,
    val registrationFinished: Boolean,
) : UiSate {
    companion object {
        /**
         * For when the state wasn't initialized
         */
        val Idle = RegistrationScreenState(
            showingContent = false,
            creatingMember = null,
            creatingFamily = null,
            syncingFamily = null,
            registrationFinished = false,
        )
    }
}
