package com.famy.us.feature.note.taskContent.machineState.states

import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState

/**
 * Machine state for when the user are editing the task content.
 */
internal class EditingContentState<Event : TaskContentScreenIntent, State : TaskContentScreenState> :
    CommonMachineState<Event, State>() {

    override fun doStart() {
        super.doStart()
        val currentState = getUiState()
        val newState = currentState.copy(
            editing = true,
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            TaskContentScreenIntent.FinishEdit -> {
                val newMachineState = LoadingContentState<Event, State>(
                    taskId = currentState.onContent?.id ?: -1
                )
                setMachineState(newMachineState)
            }
        }
    }
}
