package com.famy.us.feature.note

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.Reducer

/**
 * Class to provider the UI state according to the reduction effect.
 *
 * @param initialValue the started value for this reducer.
 */
internal class NoteScreenStateReducer(initialValue: NoteScreenState) :
    Reducer<NoteScreenState, NoteScreenIntent>(initialValue) {

    override fun reduce(oldState: NoteScreenState, event: NoteScreenIntent) {
        when (event) {
            is NoteScreenIntent.ShowContent -> {
                logD { "ShowContent" }
                val newState = oldState.copy(listTask = event.tasks, isLoading = false)
                setState(newState)
            }
            NoteScreenIntent.AddTask -> {
                logD { "AddTask" }
                val newState = oldState.copy(shouldShowDialog = true)
                setState(newState)
            }
            NoteScreenIntent.DismissDialog -> {
                logD { "DismissDialog" }
                val newState = oldState.copy(shouldShowDialog = false)
                setState(newState)
            }
            is NoteScreenIntent.SaveTask -> {
                logD { "SaveTask" }
                val newState = oldState.copy(listTask = oldState.listTask + event.task)
                setState(newState)
            }
            is NoteScreenIntent.TypingText -> {
                logD { "TypingText" }
                val newState =
                    oldState.copy(addingTask = oldState.addingTask.copy(taskName = event.value))
                setState(newState)
            }
            is NoteScreenIntent.SlidingSlider -> {
                logD { "SlidingSlider" }
                val newState =
                    oldState.copy(addingTask = oldState.addingTask.copy(sliderPosition = event.position))
                setState(newState)
            }
            is NoteScreenIntent.DeleteTask -> {}
            is NoteScreenIntent.EditTask -> {}
        }
    }
}
