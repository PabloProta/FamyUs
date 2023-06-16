package com.famy.us.core.utils.statemachine.machines

import com.famy.us.core.utils.statemachine.states.UiSate

/**
 * Interface to represent a some machine status.
 */
interface MachineStatus<State : UiSate> {

    /**
     * Method to know if the machine is started.
     */
    fun isStarted(): Boolean

    /**
     * Method to get the currentUiState.
     */
    fun getUiState(): State
}
