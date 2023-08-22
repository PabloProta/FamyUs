package com.famy.us.feature.note

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.core.utils.machines.MutableStateMachine
import com.famy.us.feature.note.states.LoadingState

/**
 * State Holder for our NoteMenu screen.
 */
internal class NoteMenuViewModel : ViewModel() {
    private fun startMachine(): CommonMachineState<NoteScreenIntent, NoteScreenState> =
        LoadingState()

    private val stateMachine = MutableStateMachine(NoteScreenState.Idle, ::startMachine)

    val uiState: MutableState<NoteScreenState>
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
