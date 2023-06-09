package com.famy.us.feature.note

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
     * @property taskId the Task id that will be deleted.
     */
    data class DeleteTask(val taskId: HomeTask) : NoteScreenIntent()

    /**
     * Intent for when user save the task created by the dialog.
     *
     * @property task the task that will be saved.
     */
    data class SaveTask(val task: HomeTask) : NoteScreenIntent()

    /**
     * When the note menu screen is opened.
     *
     * @property tasks the content that need be shown.
     */
    data class ShowContent(val tasks: List<HomeTask>) : NoteScreenIntent()

    /**
     * Intent for when the user are typing some text.
     *
     * @property value the string that are being typed.
     */
    data class TypingText(val value: String) : NoteScreenIntent()

    /**
     * Intent for when a slider in the NoteScreen are being slided.
     *
     * @property position the position that it are.
     */
    data class SlidingSlider(val position: Int) : NoteScreenIntent()

    /**
     * For when any dialog into [NoteMenuScreen] is dismissed.
     */
    object DismissDialog : NoteScreenIntent()
}
