package com.famy.us.feature.note

import com.famy.us.core.utils.UiSate
import com.famy.us.domain.model.HomeTask

/**
 * Class to represent the screen state for [NoteMenuScreen].
 *
 * @property shouldShowDialog to know if the dialog to add task need be shown.
 * @property isLoading if the content is being loaded.
 * @property listTask the tasks that was loaded.
 * @property addingTask the status for when a task that are being added.
 */
internal data class NoteScreenState(
    val shouldShowDialog: Boolean,
    val isLoading: Boolean,
    val listTask: List<HomeTask>,
    val addingTask: AddingTask,
) : UiSate {
    companion object {
        /**
         * For when the state wasn't initialized.
         */
        val Idle = NoteScreenState(
            shouldShowDialog = false,
            isLoading = true,
            listTask = emptyList(),
            addingTask = AddingTask(
                taskName = "",
                sliderPosition = 0,
            ),
        )
    }
}

/**
 * A class representing the status info for when the user are adding a task.
 *
 * @property taskName the task name.
 * @property sliderPosition the point to the task according the slider position.
 */
internal data class AddingTask(
    val taskName: String,
    val sliderPosition: Int,
)
