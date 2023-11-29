package com.famy.us.core.ui.pointerinput

import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.gestures.awaitVerticalDragOrCancellation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.drag
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException
import androidx.compose.ui.input.pointer.PointerId
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.isOutOfBounds
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.positionChangedIgnoreConsumed
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.util.fastAll
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.util.fastForEach
import com.famy.us.core.extensions.logD
import kotlinx.coroutines.CancellationException

/**
 * Gesture detector that waits for pointer down and long press, after which it calls [onDrag] for
 * each drag event.
 *
 * [onDragStart] called when a long press is detected and includes an [Offset] representing
 * the last known pointer position relative to the containing element. The [Offset] can be outside
 * the actual bounds of the element itself meaning the numbers can be negative or larger than the
 * element bounds if the touch target is smaller than the
 * [ViewConfiguration.minimumTouchTargetSize].
 *
 * [onDragEnd] is called after all pointers are up and [onDragCancel] is called if another gesture
 * has consumed pointer input, canceling this gesture. This function will automatically consume all
 * the position change after the long press.
 *
 * [pass] the pointer event pass set, by default will use [PointerEventPass.Main].
 *
 * Example Usage:
 * @sample androidx.compose.foundation.samples.DetectDragWithLongPressGesturesSample
 *
 * @see detectVerticalDragGestures
 * @see detectHorizontalDragGestures
 * @see detectDragGestures
 */
suspend fun PointerInputScope.detectDragGesturesAfterLongPress(
    pass: PointerEventPass = PointerEventPass.Main,
    onDragStart: (Offset) -> Unit = { },
    onDragEnd: () -> Unit = { },
    onDragCancel: () -> Unit = { },
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
) {
    awaitEachGesture {
        try {
            val down = awaitFirstDown(requireUnconsumed = false, pass)
            val drag = awaitLongPressOrCancellation(pass, down.id)
            if (drag != null) {
                onDragStart.invoke(drag.position)

                if (
                    drag(pass, drag.id) {
                        onDrag(it, it.positionChange())
                        it.consume()
                    }
                ) {
                    // consume up if we quit drag gracefully with the up
                    currentEvent.changes.fastForEach {
                        if (it.changedToUp()) it.consume()
                    }
                    onDragEnd()
                } else {
                    logD { "isCancelling, not draging" }
                    onDragCancel()
                }
            }
        } catch (c: CancellationException) {
            onDragCancel()
            throw c
        }
    }
}

/**
 * Waits for a long press by examining [pointerId].
 *
 * If that [pointerId] is raised (that is, the user lifts their finger), but another
 * finger ([PointerId]) is down at that time, another pointer will be chosen as the lead for the
 * gesture, and if none are down, `null` is returned.
 *
 * Ps: the default pointer event pass is [PointerEventPass.Main] but could be changed.
 *
 * @return The latest [PointerInputChange] associated with a long press or `null` if all pointers
 * are raised before a long press is detected or another gesture consumed the change.
 *
 * Example Usage:
 * @sample androidx.compose.foundation.samples.AwaitLongPressOrCancellationSample
 */
suspend fun AwaitPointerEventScope.awaitLongPressOrCancellation(
    pass: PointerEventPass = PointerEventPass.Main,
    pointerId: PointerId,
): PointerInputChange? {
    if (currentEvent.isPointerUp(pointerId)) {
        return null // The pointer has already been lifted, so the long press is cancelled.
    }

    val initialDown =
        currentEvent.changes.fastFirstOrNull { it.id == pointerId } ?: return null

    var longPress: PointerInputChange? = null
    var currentDown = initialDown
    val longPressTimeout = viewConfiguration.longPressTimeoutMillis
    return try {
        // wait for first tap up or long press
        withTimeout(longPressTimeout) {
            var finished = false
            while (!finished) {
                val event = awaitPointerEvent(pass)
                if (event.changes.fastAll { it.changedToUpIgnoreConsumed() }) {
                    // All pointers are up
                    finished = true
                }

                if (
                    event.changes.fastAny {
                        it.isConsumed || it.isOutOfBounds(size, extendedTouchPadding)
                    }
                ) {
                    finished = true // Canceled
                }

                // Check for cancel by position consumption. We can look on the Final pass of
                // the existing pointer event because it comes after the Main pass we checked
                // above.
                val consumeCheck = awaitPointerEvent(PointerEventPass.Final)
                if (consumeCheck.changes.fastAny { it.isConsumed }) {
                    finished = true
                }
                if (event.isPointerUp(currentDown.id)) {
                    val newPressed = event.changes.fastFirstOrNull { it.pressed }
                    if (newPressed != null) {
                        currentDown = newPressed
                        longPress = currentDown
                    } else {
                        // should technically never happen as we checked it above
                        finished = true
                    }
                    // Pointer (id) stayed down.
                } else {
                    longPress = event.changes.fastFirstOrNull { it.id == currentDown.id }
                }
            }
        }
        null
    } catch (_: PointerEventTimeoutCancellationException) {
        longPress ?: initialDown
    }
}

/**
 * Continues to read drag events until all pointers are up or the drag event is canceled.
 * The initial pointer to use for driving the drag is [pointerId]. [motionFromChange]
 * converts the [PointerInputChange] to the pixel change in the direction that this
 * drag should detect. [onDrag] is called whenever the pointer moves and [motionFromChange]
 * returns non-zero.
 *
 * Ps: the default pointer event pass is [PointerEventPass.Main] but could be changed.
 *
 * @return The last pointer input event change when gesture ended with all pointers up
 * and null when the gesture was canceled.
 */
suspend fun AwaitPointerEventScope.drag(
    pass: PointerEventPass = PointerEventPass.Main,
    pointerId: PointerId,
    onDrag: (PointerInputChange) -> Unit,
): Boolean {
    var pointer = pointerId
    while (true) {
        val change = awaitDragOrCancellation(pass, pointer) ?: return false

        if (change.changedToUpIgnoreConsumed()) {
            return true
        }

        onDrag(change)
        pointer = change.id
    }
}

/**
 * Reads pointer input events until a drag is detected or all pointers are up. When the  final
 * pointer is raised, the up event is returned. When a drag event is detected, the
 * drag change will be returned. Note that if [pointerId] has been raised, another pointer
 * that is down will be used, if available, so the returned [PointerInputChange.id] may
 * differ from [pointerId]. If the position change in the any direction has been
 * consumed by the [PointerEventPass.Main] pass, then the drag is considered canceled and `null`
 * is returned.  If [pointerId] is not down when [awaitDragOrCancellation] is called, then
 * `null` is returned.
 *
 * Ps: the default pointer event pass is [PointerEventPass.Main] but could be changed.
 *
 * Example Usage:
 * @sample androidx.compose.foundation.samples.AwaitDragOrCancellationSample
 *
 * @see awaitVerticalDragOrCancellation
 * @see awaitHorizontalDragOrCancellation
 * @see drag
 *
 */
suspend fun AwaitPointerEventScope.awaitDragOrCancellation(
    pass: PointerEventPass = PointerEventPass.Main,
    pointerId: PointerId,
): PointerInputChange? {
    if (currentEvent.isPointerUp(pointerId)) {
        return null // The pointer has already been lifted, so the gesture is canceled
    }
    val change = awaitDragOrUp(pass, pointerId) { it.positionChangedIgnoreConsumed() }
    return if (change?.isConsumed == false) change else null
}

/**
 * Waits for a single drag in one axis, final pointer up, or all pointers are up.
 * When [pointerId] has lifted, another pointer that is down is chosen to be the finger
 * governing the drag. When the final pointer is lifted, that [PointerInputChange] is
 * returned. When a drag is detected, that [PointerInputChange] is returned. A drag is
 * only detected when [hasDragged] returns `true`.
 *
 * `null` is returned if there was an error in the pointer input stream and the pointer
 * that was down was dropped before the 'up' was received.
 *
 * Ps: the default pointer event pass is [PointerEventPass.Main] but could be changed.
 */
private suspend inline fun AwaitPointerEventScope.awaitDragOrUp(
    pass: PointerEventPass = PointerEventPass.Main,
    pointerId: PointerId,
    hasDragged: (PointerInputChange) -> Boolean,
): PointerInputChange? {
    var pointer = pointerId
    while (true) {
        val event = awaitPointerEvent(pass)
        val dragEvent = event.changes.fastFirstOrNull { it.id == pointer } ?: return null
        if (dragEvent.changedToUpIgnoreConsumed()) {
            val otherDown = event.changes.fastFirstOrNull { it.pressed }
            if (otherDown == null) {
                // This is the last "up"
                return dragEvent
            } else {
                pointer = otherDown.id
            }
        } else if (hasDragged(dragEvent)) {
            return dragEvent
        }
    }
}

private fun PointerEvent.isPointerUp(pointerId: PointerId): Boolean =
    changes.fastFirstOrNull { it.id == pointerId }?.pressed != true
