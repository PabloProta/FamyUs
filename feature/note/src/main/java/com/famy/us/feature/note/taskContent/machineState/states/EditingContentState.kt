package com.famy.us.feature.note.taskContent.machineState.states

import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Machine state for when the user are editing the task content.
 */
internal class EditingContentState<Event : TaskContentScreenIntent, State : TaskContentScreenState> :
    CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

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
            is TaskContentScreenIntent.FinishEdit -> {
                machineScope.launch {
                    val newMachineState = LoadingContentState<Event, State>(
                        taskId = currentState.onContent?.id ?: -1,
                    )
                    homeTaskRepository.updateTask(gesture.task)
                    setMachineState(newMachineState)
                }
            }
        }
    }
}
