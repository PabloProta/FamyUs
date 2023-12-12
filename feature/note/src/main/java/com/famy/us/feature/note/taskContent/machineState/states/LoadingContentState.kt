package com.famy.us.feature.note.taskContent.machineState.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenIntent
import com.famy.us.feature.note.taskContent.machineState.TaskContentScreenState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Machine state for when the task content is being loaded.
 *
 * @property taskId the taskId tha will be loaded.
 */
internal class LoadingContentState<Event : TaskContentScreenIntent, State : TaskContentScreenState>(
    private val taskId: Int,
) :
    CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "Loading content!" }
        setUiState(TaskContentScreenState.IDLE as State)
        load()
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        if (gesture is TaskContentScreenIntent.LoadTask) {
            setMachineState(LoadingContentState(gesture.taskId))
        }
    }

    private fun load() {
        if (taskId < 0) return
        machineScope.launch {
            val note = homeTaskRepository.getTaskById(taskId).first()
            val newMachineState = TaskContentState<Event, State>(note = note)
            setMachineState(newMachineState)
        }
    }
}
