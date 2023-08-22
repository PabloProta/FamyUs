package com.famy.us.core.utils.machines

import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.UiEvent
import com.famy.us.core.utils.UiSate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Class to provide a state machine with a state flow.
 *
 * @param initialUiState the initial ui state.
 * @param init the state machine to been started.
 */
open class FlowStateMachine<Event : UiEvent, State : UiSate>(
    initialUiState: State,
    init: () -> CommonMachineState<Event, State>,
) : StateMachine.Base<Event, State>(init) {

    private val mediator = MutableStateFlow(initialUiState)

    override fun getUiState(): State = mediator.value

    /**
     * The uiState for this state machine.
     */
    val uiState: StateFlow<State> = mediator.asStateFlow()

    init {
        start()
    }

    final override fun setUiState(newState: State) {
        mediator.value = newState
    }
}
