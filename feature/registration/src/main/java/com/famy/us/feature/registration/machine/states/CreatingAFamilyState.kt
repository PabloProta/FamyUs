package com.famy.us.feature.registration.machine.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.domain.model.AdminMember
import com.famy.us.feature.registration.machine.RegistrationScreenIntent
import com.famy.us.feature.registration.machine.RegistrationScreenState

/**
 * State machine for when a family is being created.
 */
internal class CreatingAFamilyState<
    Event : RegistrationScreenIntent,
    State : RegistrationScreenState,
    > : CommonMachineState<Event, State>() {

    override fun doStart() {
        super.doStart()
        logD { "CreatingAFamilyState" }
        val currentState = getUiState()
        val newState = currentState.copy(
            creatingFamily = "",
            creatingMember = null,
        )

        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            is RegistrationScreenIntent.SaveFamily -> {
                val newUser = AdminMember(
                    id = 0,
                    name = "",
                    tasks = emptyList(),
                    score = 0,
                )
                val newState = currentState.copy(
                    creatingFamily = gesture.familyName,
                    creatingMember = newUser,
                )
                setMachineState(CreatingMemberState(newState))
            }

            RegistrationScreenIntent.Back -> {
                setMachineState(ShowRegistrationContentState())
            }
        }
    }
}
