
package com.famy.us.core.utils.statemachine.states

import androidx.compose.runtime.Stable

/**
 * A interface representing a UiState.
 * Need be used for every time that we need handle a UI state using
 * the Reducer.
 */
@Stable
interface UiSate

/**
 * A interface representing a Ui event.
 *
 * Need be used for every time that we want notify a event to a reducer class.
 */
interface UiEvent
