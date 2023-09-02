package com.famy.us.feature.note.taskContent

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.core.utils.machines.MutableStateMachine
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState
import com.famy.us.feature.note.taskContent.machineState.states.LoadingContentState

/**
 * ViewModel for the [TaskContentContainer] screen.
 */
internal class TaskContentViewModel : ViewModel() {
    private fun startMachine(): CommonMachineState<TaskContentScreenIntent, TaskContentScreenState> =
        LoadingContentState(-1)

    private val stateMachine = MutableStateMachine(TaskContentScreenState.IDLE, ::startMachine)

    val uiState: MutableState<TaskContentScreenState>
        get() = stateMachine.uiState

    /**
     * Method to perform an action in the state machine.
     *
     * @param event the event performed from UI.
     */
    fun perform(event: TaskContentScreenIntent) {
        stateMachine.process(event)
    }
}
