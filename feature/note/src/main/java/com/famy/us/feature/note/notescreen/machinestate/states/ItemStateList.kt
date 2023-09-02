package com.famy.us.feature.note.notescreen.machinestate.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Class representing the machine state.
 * This is State for when need show the task list on Note screen.
 *
 * @property taskList the list of [HomeTask].
 */
internal class ItemStateList<Event : NoteScreenIntent, State : NoteScreenState>(
    private val taskList: List<HomeTask>,
) : CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "ItemList State." }
        val newState = NoteScreenState.Idle.copy(
            showingTaskList = taskList,
            isLoading = false,
        )

        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        when (gesture) {
            NoteScreenIntent.AddTask -> {
                setMachineState(AddStateTask())
            }
            is NoteScreenIntent.DeleteTask -> {
                machineScope.launch {
                    homeTaskRepository.deleteTaskById(gesture.task.id)
                    setMachineState(LoadingState())
                }
            }
            is NoteScreenIntent.EditTask -> {
                setMachineState(EditingStateTask(gesture.task))
            }
            is NoteScreenIntent.ShowTaskContent -> {
                setMachineState(ShowTaskContentState(gesture.taskId))
            }
        }
    }
}
