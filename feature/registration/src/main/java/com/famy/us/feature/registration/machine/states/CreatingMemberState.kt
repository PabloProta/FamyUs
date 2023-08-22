package com.famy.us.feature.registration.machine.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.model.AdminMember
import com.famy.us.domain.usecase.CreateNewFamilyUseCase
import com.famy.us.feature.registration.machine.RegistrationScreenIntent
import com.famy.us.feature.registration.machine.RegistrationScreenState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Machine state for when the user is being created.
 */
internal class CreatingMemberState<Event : RegistrationScreenIntent,
    State : RegistrationScreenState,
    >(
    private val newState: RegistrationScreenState,
) : CoroutineMachineState<Event, State>(), KoinComponent {

    private val createNewFamilyUseCase: CreateNewFamilyUseCase by inject()

    override fun doStart() {
        super.doStart()
        logD { "CreatingMemberState" }
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            is RegistrationScreenIntent.SaveMember -> {
                if (currentState.creatingFamily != null) {
                    currentState.apply {
                        val newMember: AdminMember = (creatingMember as AdminMember).copy(
                            name = gesture.memberName,
                        )
                        createNewFamilyUseCase(creatingFamily.toString(), newMember)
                        setMachineState(LoadingMemberState())
                    }
                }
            }

            RegistrationScreenIntent.Back -> {
                if (currentState.creatingFamily != null) {
                    setMachineState(CreatingAFamilyState())
                }
            }
        }
    }
}
