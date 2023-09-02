package com.famy.us.feature.note.notescreen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.core.utils.machines.MutableStateMachine
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState
import com.famy.us.feature.note.notescreen.machinestate.states.LoadingState

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
     * Method to perform an action in the state machine.
     *
     * @param event the event performed from UI.
     */
    fun perform(event: NoteScreenIntent) {
        stateMachine.process(event)
    }
}
