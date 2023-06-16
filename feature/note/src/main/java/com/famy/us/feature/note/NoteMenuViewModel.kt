package com.famy.us.feature.note

import androidx.lifecycle.ViewModel
import com.famy.us.core.utils.statemachine.machines.CommonStateMachine
import com.famy.us.core.utils.statemachine.machines.FlowStateMachine
import com.famy.us.feature.note.states.LoadingState
import kotlinx.coroutines.flow.StateFlow

/**
 * State Holder for our NoteMenu screen.
 */
internal class NoteMenuViewModel : ViewModel() {
    private fun startMachine(): CommonStateMachine<NoteScreenIntent, NoteScreenState> =
        LoadingState()

    private val stateMachine = FlowStateMachine(NoteScreenState.Idle, ::startMachine)

    val uiState: StateFlow<NoteScreenState>
        get() = stateMachine.uiState

    /**
     * Method to perform a event to send to the reducer, to get a new state according
     * to the reduction.
     *
     * @param event the event performed from UI.
     */
    fun perform(event: NoteScreenIntent) {
        stateMachine.process(event)
    }
}
