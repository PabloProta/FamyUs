package com.famy.us.core.utils.machines

import com.famy.us.core.utils.UiSate

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
