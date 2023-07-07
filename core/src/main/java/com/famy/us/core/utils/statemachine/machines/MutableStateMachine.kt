package com.famy.us.core.utils.statemachine.machines

import androidx.compose.runtime.mutableStateOf
import com.famy.us.core.utils.statemachine.StateMachine
import com.famy.us.core.utils.statemachine.states.UiEvent
import com.famy.us.core.utils.statemachine.states.UiSate

/**
 * Class to provide a state machine based on mutable state.
 * @param initialUiState the initial ui state.
 * @param init the state machine to been started.
 */
class MutableStateMachine<Event : UiEvent, State : UiSate>(
    initialUiState: State,
    init: () -> CommonMachineState<Event, State>,
) : StateMachine.Base<Event, State>(init) {

    /**
     * Retrieve the current machine state for this state machine.
     */
    val uiState = mutableStateOf(initialUiState)

    init {
        start()
    }

    override fun setUiState(newState: State) {
        uiState.value = newState
    }

    override fun getUiState(): State = uiState.value
}
