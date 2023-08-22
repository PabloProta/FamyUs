package com.famy.us.feature.registration.machine.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.feature.registration.machine.RegistrationScreenIntent
import com.famy.us.feature.registration.machine.RegistrationScreenState

/**
 * Class that represents the machine state for when we need to show the registration screen content.
 */
internal class ShowRegistrationContentState<
    Event : RegistrationScreenIntent,
    State : RegistrationScreenState,> : CommonMachineState<Event, State>() {

    override fun doStart() {
        super.doStart()
        logD { "ShowRegistrationContentState" }

        setUiState(RegistrationScreenState.Idle.copy(showingContent = true) as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        when (gesture) {
            RegistrationScreenIntent.CreateFamily -> {
                setMachineState(CreatingAFamilyState())
            }

            RegistrationScreenIntent.SyncMember -> {
                // TODO - the sync member will be implemented later.
            }
        }
    }
}
