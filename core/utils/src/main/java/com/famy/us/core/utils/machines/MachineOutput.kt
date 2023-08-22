package com.famy.us.core.utils.machines

import com.famy.us.core.utils.UiEvent
import com.famy.us.core.utils.UiSate

/**
 * Interface to represent the output for the state machine.
 */
interface MachineOutput<Event : UiEvent, State : UiSate> {

    /**
     * Method to set a machine state.
     */
    fun setMachineState(machineState: CommonMachineState<Event, State>)

    /**
     * Method to set the uiState for state machine.
     */
    fun setUiState(newState: State)
}
