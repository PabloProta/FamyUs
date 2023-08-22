package com.famy.us.feature.registration.machine

import com.famy.us.core.utils.UiEvent

/**
 * This class represent all actions possible during the registration flow.
 */
sealed interface RegistrationScreenIntent : UiEvent {

    /**
     * To create a new family.
     */
    object CreateFamily : RegistrationScreenIntent

    /**
     * To create a new member.
     */
    object CreateMember : RegistrationScreenIntent

    /**
     * To sync a new member with a family.
     */
    object SyncMember : RegistrationScreenIntent

    /**
     * When the registration flow is saved after put the information.
     *
     * @property familyName the family name.
     */
    data class SaveFamily(val familyName: String) : RegistrationScreenIntent

    /**
     * Save the member.
     *
     * @property memberName the member name that will be saved.
     */
    data class SaveMember(val memberName: String) : RegistrationScreenIntent

    /**
     * Action representing the user back action.
     */
    object Back : RegistrationScreenIntent
}
