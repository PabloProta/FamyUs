package com.famy.us.feature.note.notescreen.machinestate.states

import com.famy.us.core.extensions.logD
import com.famy.us.core.extensions.logE
import com.famy.us.core.extensions.move
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
 *  State for when some item is being dragged.
 */
internal class ItemDraggedState<Event : NoteScreenIntent, State : NoteScreenState>(
    private val itemDragged: HomeTask?,
    private val currentList: List<HomeTask>,
) : CoroutineMachineState<Event, State>(), KoinComponent {

    private val homeTaskRepository: HomeTaskRepository by inject()
    override fun doStart() {
        logD { "ItemDraggedState" }
        super.doStart()
        val currentState = getUiState()
        val newState = currentState.copy(
            draggingItem = itemDragged,
            showingTaskList = currentList,
        )
        setUiState(newState as State)
    }

    override fun doProcess(gesture: Event, machine: StateMachine<Event, State>) {
        super.doProcess(gesture, machine)
        val currentState = getUiState()
        when (gesture) {
            is NoteScreenIntent.MoveNote -> {
                val list = currentState.showingTaskList
                val from = gesture.from
                val to = gesture.to
                try {
                    val fromItem = list[from]
                    val toItem = list[to]
                    val newList = list.move(from, to)
                    machineScope.launch {
                        homeTaskRepository.updateTask(fromItem.copy(position = toItem.position))
                        homeTaskRepository.updateTask(toItem.copy(position = fromItem.position))
                        setMachineState(ItemDraggedState(itemDragged, newList))
                    }
                } catch (e: Exception) {
                    logE { "Error on access list elements: ${e.message}" }
                }
            }

            is NoteScreenIntent.StopDrag -> {
                setMachineState(ItemStateList(currentList))
            }
        }
    }
}
