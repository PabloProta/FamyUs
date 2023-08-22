package com.famy.us.feature.registration.machine.states

import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.feature.registration.machine.RegistrationScreenIntent
import com.famy.us.feature.registration.machine.RegistrationScreenState

/**
 * Machine state for when user finish the registration flow.
 */
internal class FinishRegistrationState<Event : RegistrationScreenIntent,
    State : RegistrationScreenState,
    > : CommonMachineState<Event, State>() {

    override fun doStart() {
        super.doStart()
        setUiState(
            RegistrationScreenState.Idle.copy(
                registrationFinished = true,
            ) as State,
        )
    }
}
