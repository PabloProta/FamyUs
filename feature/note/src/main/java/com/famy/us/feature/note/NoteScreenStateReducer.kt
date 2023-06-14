package com.famy.us.feature.note

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.Reducer
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class to provider the UI state according to the reduction effect.
 *
 * @property homeTaskRepository is the repository to make some updates in the repository side.
 */
internal class NoteScreenStateReducer(private val homeTaskRepository: HomeTaskRepository) :
    Reducer<NoteScreenState, NoteScreenIntent>(NoteScreenState.Idle) {

    @Suppress("LongMethod")
    override suspend fun reduce(oldState: NoteScreenState, event: NoteScreenIntent) {
        when (event) {
            is NoteScreenIntent.ShowContent -> {
                logD { "ShowContent" }
                withContext(Dispatchers.IO) {
                    homeTaskRepository.getAllTask().collect {
                        val newState = oldState.copy(listTask = it, isLoading = false)
                        setState(newState)
                    }
                }
            }
            NoteScreenIntent.AddTask -> {
                logD { "AddTask" }
                val newState = oldState.copy(
                    showDialog = ShowDialog(
                        shouldShowDialog = true,
                        isEditingTask = true,
                        isAddingTask = true,
                    ),
                    managingTask = HomeTask.Empty,
                )
                setState(newState)
            }
            NoteScreenIntent.DismissDialog -> {
                logD { "DismissDialog" }
                val newState = oldState.copy(
                    showDialog = ShowDialog(
                        shouldShowDialog = false,
                        isEditingTask = false,
                        isAddingTask = false,
                    ),
                )
                setState(newState)
            }
            is NoteScreenIntent.SaveTask -> {
                logD { "SaveTask" }
                withContext(Dispatchers.IO) {
                    if (event.isNewOne) {
                        homeTaskRepository.saveTask(event.task)
                    } else {
                        homeTaskRepository.updateTask(event.task)
                    }
                }
                val newState = oldState.copy(
                    listTask = oldState.listTask + event.task,
                    showDialog = ShowDialog.Reset,
                )
                setState(newState)
            }
            is NoteScreenIntent.TypingText -> {
                logD { "TypingText" }
                val newState =
                    oldState.copy(managingTask = oldState.managingTask.copy(name = event.value))
                setState(newState)
            }
            is NoteScreenIntent.SlidingSlider -> {
                logD { "SlidingSlider" }
                val newState =
                    oldState.copy(managingTask = oldState.managingTask.copy(point = event.position))
                setState(newState)
            }
            is NoteScreenIntent.ShowTaskContent -> {
                logD { "ShowTaskContent" }
                val newState =
                    oldState.copy(
                        showDialog = oldState.showDialog.copy(
                            shouldShowDialog = true,
                            isEditingTask = false,
                        ),
                        managingTask = event.task,
                    )
                setState(newState)
            }
            is NoteScreenIntent.DeleteTask -> {
                logD { "DeleteTask" }
                withContext(Dispatchers.IO) {
                    homeTaskRepository.deleteTaskById(event.task.id)
                }
            }
            is NoteScreenIntent.EditTask -> {
                logD { "EditTask" }
                val newState =
                    oldState.copy(
                        showDialog = ShowDialog(
                            shouldShowDialog = true,
                            isEditingTask = true,
                            isAddingTask = false,
                        ),
                        managingTask = event.task,
                    )
                setState(newState)
            }
        }
    }
}
