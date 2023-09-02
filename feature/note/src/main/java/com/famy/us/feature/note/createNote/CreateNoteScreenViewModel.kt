package com.famy.us.feature.note.createNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import kotlinx.coroutines.launch

internal class CreateNoteScreenViewModel(
    private val homeTaskRepository: HomeTaskRepository,
) : ViewModel() {


    fun createTask(note: HomeTask) {
        viewModelScope.launch {
            homeTaskRepository.saveTask(note)
        }
    }
}
