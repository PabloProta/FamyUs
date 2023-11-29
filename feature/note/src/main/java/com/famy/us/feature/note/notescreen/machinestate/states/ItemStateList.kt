package com.famy.us.feature.note.notescreen.machinestate.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CommonMachineState
import com.famy.us.domain.model.HomeTask
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState

/**
 * Class representing the machine state.
 * This is State for when need show the task list on Note screen.
 *
 * @property taskList the list of [HomeTask].
 */
internal class ItemStateList<Event : NoteScreenIntent, State : NoteScreenState>(
    private val taskList: List<HomeTask>,
) : CommonMachineState<Event, State>() {

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
            is NoteScreenIntent.ShowTaskContent -> {
                setMachineState(ShowTaskContentState(gesture.taskId))
            }
            is NoteScreenIntent.NoteSelected -> {
                setMachineState(NoteSelectedState(listOf(gesture.noteIndex)))
            }
        }
    }
}
