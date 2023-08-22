package com.famy.us.core.utils.machines

import androidx.compose.runtime.mutableStateOf
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.UiEvent
import com.famy.us.core.utils.UiSate

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
