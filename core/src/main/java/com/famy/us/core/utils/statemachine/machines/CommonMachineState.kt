package com.famy.us.core.utils.statemachine.machines

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.statemachine.StateMachine
import com.famy.us.core.utils.statemachine.states.UiEvent
import com.famy.us.core.utils.statemachine.states.UiSate

/**
 * This is the state machine common between any other type of state machine.
 * every state machine has this kind methods. This class just serves as abstraction.
 */
abstract class CommonMachineState<Event : UiEvent, State : UiSate> {
    private lateinit var machine: StateMachine<Event, State>

    /**
     * Start the state.
     */
    fun start(output: StateMachine<Event, State>) {
        machine = output
        doStart()
    }

    /**
     * Occur after [setMachineState].
     * when the start is done.
     */
    protected open fun doStart() {}

    /**
     * Process the state.
     *
     * @param gesture the gesture to process.
     */
    fun process(gesture: Event) {
        doProcess(gesture, machine)
    }

    fun getUiState(): State = machine.getUiState()

    /**
     * When the process is done.
     */
    protected open fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        logD { "Gesture: ${gesture::class.simpleName}" }
    }

    /**
     * Set the UI state for the current machine.
     */
    fun setUiState(newState: State) {
        machine.setUiState(newState)
    }

    /**
     *  update the Machine state.
     */
    protected fun setMachineState(machineState: CommonMachineState<Event, State>) {
        machine.setMachineState(machineState)
    }

    /**
     * When the state is passed we need to clean it.
     */
    fun clear() {
        doClear()
    }

    /**
     * When the cleanup is done.
     */
    protected open fun doClear() {}
}
