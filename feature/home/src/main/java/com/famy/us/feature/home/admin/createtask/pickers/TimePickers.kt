package com.famy.us.feature.home.admin.createtask.pickers

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import com.famy.us.core.ui.components.TimePickerDialog
import com.famy.us.core.ui.primary_100
import com.famy.us.core.ui.primary_200
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_900
import java.util.Calendar
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerInput(
    calendar: Calendar?,
    onCancel: () -> Unit,
    onConfirm: (Calendar) -> Unit,
) {
    var currentDate = calendar
    if (currentDate == null) {
        currentDate = Calendar.getInstance()
    }
    val timerState = rememberTimePickerState(
        initialHour = currentDate?.get(Calendar.HOUR_OF_DAY) ?: 0,
        initialMinute = currentDate?.get(Calendar.MINUTE) ?: 0,
    )
    TimePickerDialog(
        onCancel = onCancel,
        onConfirm = {
            currentDate?.set(Calendar.HOUR_OF_DAY, timerState.hour)
            currentDate?.set(Calendar.MINUTE, timerState.minute)
            currentDate?.isLenient = false
            currentDate?.also(onConfirm)
        },
        containerColor = tertiary_50,
    ) {
        TimePicker(
            state = timerState,
            colors = TimePickerDefaults.colors(
                containerColor = tertiary_50,
                selectorColor = tertiary_900,
                periodSelectorSelectedContainerColor = primary_200,
                timeSelectorSelectedContainerColor = primary_100,
            ),
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateDialogInput(
    calendar: Calendar,
    onDismiss: () -> Unit,
    onConfirm: (Calendar) -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val daySelected = Calendar.getInstance().apply {
                        timeInMillis =
                            datePickerState.selectedDateMillis?.plus(1.days.inWholeMilliseconds)
                                ?: 0
                    }
                    calendar.set(Calendar.DAY_OF_YEAR, daySelected.get(Calendar.DAY_OF_YEAR))
                    onConfirm(calendar)
                },
            ) {
                Text("OK")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}
