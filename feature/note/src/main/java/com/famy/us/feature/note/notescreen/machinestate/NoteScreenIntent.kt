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

    /**
     * When some note is dragged.
     *
     * @property itemDragged the item dragged index according to the list.
     */
    data class DragNote(val itemDragged: Int?) : NoteScreenIntent()

    /**
     * When the user is moving some note.
     *
     * @property from position where item come from.
     * @property to position that the item will be moved.
     */
    data class MoveNote(val from: Int, val to: Int) : NoteScreenIntent()

    /**
     * When the user make an action to select a note with a long press or a click when is
     * already selecting.
     *
     * @property noteIndex the index of the note that was selected.
     */
    data class NoteSelected(val noteIndex: Int) : NoteScreenIntent()

    /**
     * When the checkbox to select all notes is clicked.
     *
     * @property selected if the checkbox was selected or not.
     */
    data class SelectAllNotes(val selected: Boolean) : NoteScreenIntent()

    /**
     * When the user dispatch done action for notes.
     *
     * @property notes the notes that will be marked as done.
     */
    data class DoneNotes(val notes: List<Int>) : NoteScreenIntent()

    /**
     * When the user dispatch a delete action for notes.
     *
     * @property notes the notes that will be excluded.
     */
    data class DeleteNotes(val notes: List<Int>) : NoteScreenIntent()

    /**
     * When the user stop the drag action.
     */
    object StopDrag : NoteScreenIntent()

    /**
     * Intent representing the back action.
     */
    object DoBack : NoteScreenIntent()

    /**
     * Object representing that user intent to reorder de list.
     */
    object ReorderList : NoteScreenIntent()
}
