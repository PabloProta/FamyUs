package com.famy.us.feature.registration

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.core.utils.machines.MutableStateMachine
import com.famy.us.feature.registration.machine.RegistrationScreenIntent
import com.famy.us.feature.registration.machine.RegistrationScreenState
import com.famy.us.feature.registration.machine.states.LoadingMemberState

/**
 * View model class to the registration screen.
 */
class RegistrationViewModel : ViewModel() {

    private fun startMachine(): CommonMachineState<RegistrationScreenIntent, RegistrationScreenState> =
        LoadingMemberState()

    private val stateMachine = MutableStateMachine(RegistrationScreenState.Idle, ::startMachine)

    val uiState: MutableState<RegistrationScreenState>
        get() = stateMachine.uiState

    /**
     * Method to create a family.
     */
    fun perform(event: RegistrationScreenIntent) {
        stateMachine.process(event)
    }
}
