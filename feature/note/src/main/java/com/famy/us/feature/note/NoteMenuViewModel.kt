package com.famy.us.feature.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famy.us.domain.repository.HomeTaskRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * State Holder for our NoteMenu screen.
 *
 * @property homeTaskRepository is the repository to make some updates in the repository side.
 */
internal class NoteMenuViewModel(
    private val homeTaskRepository: HomeTaskRepository,
) : ViewModel() {

    private val reducer: NoteScreenStateReducer = NoteScreenStateReducer(NoteScreenState.Idle)

    val uiState: StateFlow<NoteScreenState>
        get() = reducer.state

    init {
        viewModelScope.launch {
            homeTaskRepository.getAllTask().collect {
                reducer.sendEvent(NoteScreenIntent.ShowContent(it))
            }
        }
    }

    /**
     * Method to perform a event to send to the reducer, to get a new state according
     * to the reduction.
     *
     * @param event the event performed from UI.
     */
    fun perform(event: NoteScreenIntent) {
        when (event) {
            is NoteScreenIntent.SaveTask -> {
                viewModelScope.launch {
                    homeTaskRepository.saveTask(event.task)
                }
            }
            else -> {
                // Do nothing
            }
        }
        reducer.sendEvent(event)
    }
}
