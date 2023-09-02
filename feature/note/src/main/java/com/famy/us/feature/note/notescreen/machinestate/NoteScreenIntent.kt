package com.famy.us.feature.note.notescreen.machinestate

import com.famy.us.core.utils.UiEvent
import com.famy.us.domain.model.HomeTask

/**
 * Class containing all intent possibles to be handled at Note screen.
 */
internal sealed class NoteScreenIntent : UiEvent {
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
     * @property task the Task that will be deleted.
     */
    data class DeleteTask(val task: HomeTask) : NoteScreenIntent()

    /**
     * Intent for when user save the task created by the dialog.
     *
     * @property task the task that will be saved.
     */
    data class SaveTask(val task: HomeTask) : NoteScreenIntent()

    /**
     * For when any dialog into [NoteMenuScreenContainer] is dismissed.
     */
    object DismissTaskContent : NoteScreenIntent()

    /**
     * When the user click in the task card content.
     *
     * @property taskId the taskId of the task that will be shown.
     */
    data class ShowTaskContent(val taskId: Int) : NoteScreenIntent()
}
