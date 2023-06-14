package com.famy.us.feature.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * State Holder for our NoteMenu screen.
 *
 * @property reducer the class responsible to reduce the state from new states.
 */
internal class NoteMenuViewModel(
    private val reducer: NoteScreenStateReducer,
) : ViewModel() {

    val uiState: StateFlow<NoteScreenState>
        get() = reducer.state

    init {
        viewModelScope.launch {
            reducer.sendEvent(NoteScreenIntent.ShowContent)
        }
    }

    /**
     * Method to perform a event to send to the reducer, to get a new state according
     * to the reduction.
     *
     * @param event the event performed from UI.
     */
    fun perform(event: NoteScreenIntent) {
        viewModelScope.launch {
            reducer.sendEvent(event)
        }
    }
}
