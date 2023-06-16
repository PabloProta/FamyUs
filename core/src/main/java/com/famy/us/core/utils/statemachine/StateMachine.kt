package com.famy.us.core.utils.statemachine

import com.famy.us.core.utils.statemachine.machines.CommonStateMachine
import com.famy.us.core.utils.statemachine.machines.MachineInput
import com.famy.us.core.utils.statemachine.machines.MachineOutput
import com.famy.us.core.utils.statemachine.machines.MachineStatus
import com.famy.us.core.utils.statemachine.states.UiEvent
import com.famy.us.core.utils.statemachine.states.UiSate

/**
 * Interface to represent a state machine.
 */
interface StateMachine<Event : UiEvent, State : UiSate> :
    MachineStatus<State>,
    MachineInput<Event>,
    MachineOutput<Event, State> {

    /**
     * A class with the common implementations for each state machine methods.
     *
     * @property init a [StateMachine] to initialize the State machine.
     */
    abstract class Base<Event : UiEvent, State : UiSate>(
        private val init: () -> CommonStateMachine<Event, State>,
    ) : StateMachine<Event, State> {

        private lateinit var currentState: CommonStateMachine<Event, State>

        private var started: Boolean = false

        final override fun isStarted(): Boolean = started

        override fun setMachineState(machineState: CommonStateMachine<Event, State>) {
            clear()
            currentState = machineState
            startMachineState()
        }

        /**
         * Start manually the state machine.
         */
        protected fun start() {
            if (started.not()) {
                currentState = init()
                startMachineState()
            }
        }

        /**
         * Start the machine state.
         */
        private fun startMachineState() {
            started = true
            currentState.start(this)
        }

        final override fun process(gesture: Event) {
            currentState.process(gesture)
        }

        /**
         * Cleans-up state machine.
         */
        final override fun clear() {
            currentState.clear()
        }
    }
}
