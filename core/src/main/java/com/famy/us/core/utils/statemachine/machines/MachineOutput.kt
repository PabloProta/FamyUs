package com.famy.us.core.utils.statemachine.machines

import com.famy.us.core.utils.statemachine.states.UiEvent
import com.famy.us.core.utils.statemachine.states.UiSate

/**
 * Interface to represent the output for the state machine.
 */
interface MachineOutput<Event : UiEvent, State : UiSate> {

    /**
     * Method to set a machine state.
     */
    fun setMachineState(machineState: CommonStateMachine<Event, State>)

    /**
     * Method to set the uiState for state machine.
     */
    fun setUiState(newState: State)
}
