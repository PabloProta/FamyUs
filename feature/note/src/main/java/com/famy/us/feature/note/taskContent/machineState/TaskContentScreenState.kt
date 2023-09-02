package com.famy.us.feature.note.taskContent.machineState

import com.famy.us.core.utils.UiSate
import com.famy.us.domain.model.HomeTask

/**
 * Model representing the Task content screen ui state.
 *
 * @property loading if the task is loading.
 * @property onContent when a task is loaded.
 * @property editing when a task is being edited.
 * @property deleting if a task is being deleted.
 */
internal data class TaskContentScreenState(
    val loading: Boolean,
    val onContent: HomeTask?,
    val editing: Boolean,
    val deleting: Boolean,
) : UiSate {
    companion object {
        /**
         * The initial state for the ui.
         */
        val IDLE = TaskContentScreenState(
            loading = false,
            onContent = null,
            editing = false,
            deleting = false,
        )
    }
}
