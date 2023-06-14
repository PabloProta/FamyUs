package com.famy.us.core.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This is a class responsible to give a abstraction of class that can work as a
 * reducer.
 * For get more about this pattern check this articles:
 * [first](https://redux.js.org/tutorials/essentials/part-1-overview-concepts)
 * [second](https://ivanmontiel.medium.com/discovering-the-state-reducer-pattern-3f324bb1a4c4)
 *
 * @param initialValue is the initial value set to this reducer.
 *
 * @see UiSate is the State given by this Reducer.
 * @see UiEvent is the event input for the reducer work.
 */
abstract class Reducer<State : UiSate, Event : UiEvent>(initialValue: State) {
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialValue)

    val state: StateFlow<State>
        get() = _state

    abstract suspend fun reduce(oldState: State, event: Event)

    suspend fun sendEvent(event: Event) {
        reduce(_state.value, event)
    }

    fun setState(newState: State) {
        _state.tryEmit(newState)
    }
}

/**
 * A interface representing a UiState.
 * Need be used for every time that we need handle a UI state using
 * the Reducer.
 */
interface UiSate

/**
 * A interface representing a Ui event.
 *
 * Need be used for every time that we want notify a event to a reducer class.
 */
interface UiEvent
