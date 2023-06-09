package com.famy.us.feature.note.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.statemachine.StateMachine
import com.famy.us.core.utils.statemachine.machines.CoroutineMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.NoteScreenIntent
import com.famy.us.feature.note.NoteScreenState
import com.famy.us.feature.note.ShowDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * This is the State for the state machine for when the user are
 * adding some task.
 *
 * @param homeTask the task that it is editing.
 */
internal class AddStateTask<Event : NoteScreenIntent, State : NoteScreenState>(
    private val homeTask: HomeTask,
) : CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "AddTaskState" }

        val newState = getUiState().copy(
            showDialog = ShowDialog(
                shouldShowDialog = true,
                isAddingTask = true,
            ),
            isLoading = false,
            managingTask = homeTask,
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            is NoteScreenIntent.DismissDialog -> {
                setMachineState(ItemStateList(currentState.listTask))
            }
            is NoteScreenIntent.SaveTask -> {
                machineScope.launch(Dispatchers.IO) {
                    currentState.apply {
                        homeTaskRepository.saveTask(gesture.task)
                        setMachineState(LoadingState(justFetch = true))
                    }
                }
            }
        }
    }
}
