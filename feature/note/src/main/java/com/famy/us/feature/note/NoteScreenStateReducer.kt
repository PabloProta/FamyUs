package com.famy.us.feature.note

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.Reducer
import com.famy.us.domain.model.HomeTask

/**
 * Class to provider the UI state according to the reduction effect.
 *
 * @param initialValue the started value for this reducer.
 */
internal class NoteScreenStateReducer(initialValue: NoteScreenState) :
    Reducer<NoteScreenState, NoteScreenIntent>(initialValue) {

    @Suppress("LongMethod")
    override fun reduce(oldState: NoteScreenState, event: NoteScreenIntent) {
        when (event) {
            is NoteScreenIntent.ShowContent -> {
                logD { "ShowContent" }
                val newState = oldState.copy(listTask = event.tasks, isLoading = false)
                setState(newState)
            }
            NoteScreenIntent.AddTask -> {
                logD { "AddTask" }
                val newState = oldState.copy(
                    showDialog = ShowDialog(
                        shouldShowDialog = true,
                        isEditingTask = true,
                        isAddingTask = true
                    ),
                    managingTask = HomeTask.Empty
                )
                setState(newState)
            }
            NoteScreenIntent.DismissDialog -> {
                logD { "DismissDialog" }
                val newState = oldState.copy(
                    showDialog = ShowDialog(
                        shouldShowDialog = false,
                        isEditingTask = false,
                        isAddingTask = false
                    )
                )
                setState(newState)
            }
            is NoteScreenIntent.SaveTask -> {
                logD { "SaveTask" }
                val newState = oldState.copy(
                    listTask = oldState.listTask + event.task,
                    showDialog = ShowDialog.Reset
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
                            isEditingTask = false
                        ),
                        managingTask = event.task
                    )
                setState(newState)
            }
            is NoteScreenIntent.DeleteTask -> {
                logD { "DeleteTask" }
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
                        managingTask = event.task
                    )
                setState(newState)
            }
        }
    }
}
