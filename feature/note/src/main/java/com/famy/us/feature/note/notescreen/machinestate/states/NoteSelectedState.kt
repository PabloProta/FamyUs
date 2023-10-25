package com.famy.us.feature.note.notescreen.machinestate.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.utils.StateMachine
import com.famy.us.core.utils.machines.CoroutineMachineState
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenIntent
import com.famy.us.feature.note.notescreen.machinestate.NoteScreenState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * This is the machine state for when user start to select some note with a long press.
 *
 * @property notesSelected the index of the note selected.
 */
internal class NoteSelectedState<Event : NoteScreenIntent, State : NoteScreenState>(
    private val notesSelected: List<Int>,
) : CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()

    override fun doStart() {
        super.doStart()
        logD { "NoteSelectedState | selected: $notesSelected" }
        val currentState = getUiState()
        val newState = currentState.copy(
            selectingNotes = notesSelected,
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            is NoteScreenIntent.NoteSelected -> {
                val alreadySelected = notesSelected.toSet().firstOrNull { it == gesture.noteIndex }
                if (alreadySelected != null) {
                    val newList = notesSelected.filterNot { it == alreadySelected }
                    if (newList.isEmpty()) {
                        setMachineState(ItemStateList(currentState.showingTaskList))
                    } else {
                        setMachineState(NoteSelectedState(newList))
                    }
                } else {
                    val newList = notesSelected + gesture.noteIndex
                    setMachineState(NoteSelectedState(newList))
                }
            }

            is NoteScreenIntent.DeleteNotes -> {
                val notesToDelete = currentState.showingTaskList.filter { task ->
                    gesture.notes.firstOrNull { it == task.position } != null
                }.toSet()
                val newList = currentState.showingTaskList - notesToDelete
                machineScope.launch {
                    notesToDelete.forEach {
                        homeTaskRepository.deleteTaskById(it.id)
                    }
                    setMachineState(ItemStateList(newList))
                }
            }

            is NoteScreenIntent.DoneNotes -> {
                // TODO - NEED Implement the done action at the repository level.
                setMachineState(ItemStateList(currentState.showingTaskList))
            }
        }
    }
}
