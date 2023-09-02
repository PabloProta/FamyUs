package com.famy.us.feature.note.taskContent.machineState.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState

/**
 * Machine state for when the task content was loaded.
 *
 * @property note the task that was loaded.
 */
internal class TaskContentState<Event : TaskContentScreenIntent, State : TaskContentScreenState>(
    private val note: HomeTask,
) : CommonMachineState<Event, State>() {

    override fun doStart() {
        super.doStart()
        logD { "Task content loaded!" }
        val currentState = getUiState() as State
        val newState = currentState.copy(
            onContent = note,
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        when (gesture) {
            TaskContentScreenIntent.DeleteTask -> {}

            TaskContentScreenIntent.EditTask -> {}
        }
    }
}
