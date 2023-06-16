package com.famy.us.core.utils.statemachine.machines

import com.famy.us.core.utils.statemachine.states.UiEvent

/**
 * Interface to represent the input for the state machine.
 */
interface MachineInput<Gesture : UiEvent> {

    /**
     * Method to process the input.
     *
     * @param gesture the gesture caught.
     */
    fun process(gesture: Gesture)

    /**
     * Method to clear the input.
     */
    fun clear()
}
