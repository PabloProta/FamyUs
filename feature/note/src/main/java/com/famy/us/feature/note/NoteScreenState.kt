package com.famy.us.feature.note

import com.famy.us.core.utils.UiSate
import com.famy.us.domain.model.HomeTask

/**
 * Class to represent the screen state for [NoteMenuScreen].
 *
 * @property isLoading if the content is being loaded.
 * @property listTask the tasks that was loaded.
 * @property managingTask the status for when a task that are being managed.
 */
internal data class NoteScreenState(
    val showDialog: ShowDialog,
    val isLoading: Boolean,
    val listTask: List<HomeTask>,
    val managingTask: HomeTask,
) : UiSate {
    companion object {
        /**
         * For when the state wasn't initialized.
         */
        val Idle = NoteScreenState(
            showDialog = ShowDialog.Reset,
            isLoading = true,
            listTask = emptyList(),
            managingTask = HomeTask.Empty,
        )
    }
}

/**
 * Class to represent the status info for when should show the dialog about a home task.
 *
 * @property shouldShowDialog to know if the dialog to add task need be shown.
 * @property isEditingTask to know if the dialog was opened in the edit mode to allow change the
 * fields according or if is just to visualize the task content.
 * @property isAddingTask to know if the dialog was opened to add a new task.
 */
internal data class ShowDialog(
    val shouldShowDialog: Boolean,
    val isAddingTask: Boolean,
    val isEditingTask: Boolean = isAddingTask,
) {
    companion object {
        val Reset: ShowDialog = ShowDialog(
            shouldShowDialog = false,
            isEditingTask = false,
            isAddingTask = false,
        )
    }
}
