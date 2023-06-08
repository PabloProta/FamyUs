package com.famy.us.feature.note

import com.famy.us.domain.model.HomeTask

/**
 * Class containing all intent possibles to be handled at Note screen.
 */
internal sealed class NoteScreenIntent {
    /**
     * Intent to add a new task
     */
    object AddTask : NoteScreenIntent()

    /**
     * Intent to edit a task.
     *
     * @property task the Task that will be deleted.
     */
    data class EditTask(val task: HomeTask) : NoteScreenIntent()

    /**
     * Intent to delete a task.
     *
     * @property taskId the Task id that will be deleted.
     */
    data class DeleteTask(val taskId: HomeTask) : NoteScreenIntent()

    /**
     * When a back gesture is dispatched.
     */
    object Back : NoteScreenIntent()

    /**
     * Intent for when user save the task created by the dialog.
     *
     * @property task the task that will be saved.
     */
    data class SaveTask(val task: HomeTask) : NoteScreenIntent()

    /**
     * When the menu item is clicked.
     */
    object ClickMenuItem : NoteScreenIntent()
}
