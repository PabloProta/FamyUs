package com.famy.us.feature.note.taskContent.machineState

import com.famy.us.core.utils.UiEvent
import com.famy.us.domain.model.HomeTask

/**
 * Sealed interface that represents all intent actions possible at the task content screen.
 */
internal sealed interface TaskContentScreenIntent : UiEvent {

    /**
     * Representing the delete action.
     */
    object DeleteTask : TaskContentScreenIntent

    /**
     * Representing the Edit Action.
     */
    object EditTask : TaskContentScreenIntent

    /**
     * When user fish the task edition.
     *
     * @property task the task that was updated.
     */
    data class FinishEdit(val task: HomeTask) : TaskContentScreenIntent

    /**
     * When load an task is requested.
     *
     * @property taskId the task that was requested.
     */
    data class LoadTask(val taskId: Int) : TaskContentScreenIntent
}
