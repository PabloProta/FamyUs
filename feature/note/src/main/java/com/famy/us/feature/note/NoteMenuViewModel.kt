package com.famy.us.feature.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famy.us.core.extensions.logD
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * State Holder for our NoteMenu screen.
 *
 * @property homeTaskRepository is the repository to make some updates in the repository side.
 */
internal class NoteMenuViewModel(
    private val homeTaskRepository: HomeTaskRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<NoteScreenState> = MutableStateFlow(NoteScreenState.Idle)
    val uiState = _uiState.asStateFlow()

    private var currentTasks: List<HomeTask> = emptyList()

    fun perform(event: NoteScreenIntent) {
        val currentState = _uiState.value
        when (event) {
            NoteScreenIntent.ClickMenuItem -> {
                logD { "ClickMenuItem" }
                viewModelScope.launch {
                    homeTaskRepository.getAllTask().map {
                        currentTasks = it
                    }
                    _uiState.value = NoteScreenState.NoteScreenMenu(currentTasks)
                }
            }
            NoteScreenIntent.AddTask -> {
                logD { "AddTask" }
                _uiState.value = NoteScreenState.AddingTask
            }
            NoteScreenIntent.Back -> {
                if (currentState is NoteScreenState.AddingTask) {
                    logD { "Back - Closing Dialog" }
                    _uiState.value = NoteScreenState.NoteScreenMenu(currentTasks)
                }
            }
            is NoteScreenIntent.SaveTask -> {
                currentTasks = currentTasks + event.task
                viewModelScope.launch {
                    logD { "SaveTask" }
                    homeTaskRepository.saveTask(event.task)
                    _uiState.value = NoteScreenState.NoteScreenMenu(currentTasks)
                }
            }
            is NoteScreenIntent.DeleteTask -> {}
            is NoteScreenIntent.EditTask -> {}
        }
    }
}
