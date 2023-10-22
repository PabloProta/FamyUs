package com.famy.us.feature.note.notescreen.machinestate

import com.famy.us.core.utils.UiSate
import com.famy.us.domain.model.HomeTask

/**
 * Class to represent the screen state for [NoteMenuScreenContainer].
 *
 * @property isAddingTask if a task is being added.
 * @property isLoading if the content is being loaded.
 * @property showingTaskList the tasks that was loaded.
 * @property editingTask if a task is being edited.
 * @property goingToShowTaskContent if the user asked to show the note content.
 * @property draggingItem the item that is being dragged.
 */
internal data class NoteScreenState(
    val isAddingTask: Boolean,
    val editingTask: HomeTask?,
    val goingToShowTaskContent: Int,
    val isLoading: Boolean,
    val showingTaskList: List<HomeTask>,
    val draggingItem: HomeTask?,
) : UiSate {
    companion object {
        /**
         * For when the state wasn't initialized.
         */
        val Idle = NoteScreenState(
            isAddingTask = false,
            editingTask = null,
            goingToShowTaskContent = -1,
            isLoading = false,
            showingTaskList = mutableListOf(),
            draggingItem = null,
        )
    }
}
