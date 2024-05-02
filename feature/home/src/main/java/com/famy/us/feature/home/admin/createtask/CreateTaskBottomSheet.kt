package com.famy.us.feature.home.admin.createtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H6
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.components.DefaultTextField
import com.famy.us.core.ui.components.TimePickerDialog
import com.famy.us.core.ui.primary_100
import com.famy.us.core.ui.primary_200
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_600
import com.famy.us.core.ui.tertiary_900
import com.famy.us.core.utils.resources.IconResource
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember
import com.famy.us.home.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskBottomContainer(
    first: NonAdminMember,
    members: List<NonAdminMember>,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showTimerPicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var currentDate: Calendar? by remember { mutableStateOf(null) }
    Box {
        ModalBottomSheet(
            modifier = Modifier
                .wrapContentSize(),
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = Color.White,
            contentColor = Color.White,
        ) {
            CreateTaskBottomSheet(
                first = first,
                members = members,
                onClickDateInput = {
                    showTimerPicker = true
                },
            )
        }
        if (showTimerPicker) {
            TimePickerInput(
                calendar = currentDate,
                onCancel = {
                    showTimerPicker = false
                },
                onConfirm = {
                    currentDate = it
                    showTimerPicker = false
                    showDatePicker = true
                },
            )
        }
        if (showDatePicker) {
            // Do something...
        }
    }
}

@Composable
private fun CreateTaskBottomSheet(
    first: NonAdminMember,
    members: List<NonAdminMember>,
    onClickDateInput: () -> Unit,
) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(24.dp),
    ) {
        Text(
            text = "Nova Task",
            style = H6,
            color = tertiary_900,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Selecione um controlado e preencha os dados abaixo corretamente",
            style = BodySmallRegular,
            color = tertiary_600,
        )
        Spacer(modifier = Modifier.size(16.dp))
        MemberTaskSelector(
            first = first,
            members = members,
        )
        Spacer(modifier = Modifier.size(16.dp))
        NameInput(
            value = taskName,
            onValueChange = {
                taskName = it
            },
        )
        Spacer(modifier = Modifier.size(8.dp))
        DescriptionInput(
            value = taskDescription,
            onValueChange = {
                taskDescription = it
            },
        )
        Spacer(modifier = Modifier.size(8.dp))
        DatePickerInput(
            onClick = onClickDateInput,
        )
        Spacer(modifier = Modifier.size(8.dp))
        ScorePickerInput(
            onClick = {},
        )
        Column {
            Spacer(
                modifier = Modifier
                    .heightIn(min = 8.dp, max = 32.dp)
                    .fillMaxHeight(),
            )
            CreateTaskButton(
                onClickToCreate = {},
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun NameInput(
    value: String,
    onValueChange: (String) -> Unit,
) {
    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = IconResource.fromDrawableResource(R.drawable.ic_task).asPainterResource(),
                contentDescription = null,
            )
        },
        label = "Nome da Tarefa",
    )
}

@Composable
private fun DescriptionInput(
    value: String,
    onValueChange: (String) -> Unit,
) {
    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = IconResource.fromDrawableResource(R.drawable.ic_description)
                    .asPainterResource(),
                contentDescription = null,
            )
        },
        label = "Descrição da Tarefa",
    )
}

@Composable
private fun DatePickerInput(
    onClick: () -> Unit,
) {
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        value = "",
        readOnly = true,
        onValueChange = {},
        leadingIcon = {
            Icon(
                painter = IconResource.fromDrawableResource(R.drawable.ic_clock)
                    .asPainterResource(),
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = tertiary_600,
                )
            }
        },
        label = "Horário",
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerInput(
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

@Composable
private fun ScorePickerInput(
    onClick: () -> Unit,
) {
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        value = "",
        readOnly = true,
        onValueChange = {},
        leadingIcon = {
            Icon(
                painter = IconResource.fromDrawableResource(R.drawable.ic_score)
                    .asPainterResource(),
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = tertiary_600,
                )
            }
        },
        label = "Pontos",
    )
}

@Composable
private fun CreateTaskButton(
    onClickToCreate: () -> Unit,
) {
    DefaultButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClickToCreate,
    ) {
        Text(
            text = "Criar Task",
            style = ButtonMedium,
            color = Color.White,
        )
    }
}

@Preview
@Composable
internal fun CreateTaskBottomPreview() {
    val memberList = listOf(
        NonAdminMember(
            id = 0,
            name = "Kelly",
            tasks = emptyList<HomeTask>(),
            score = 0,
        ),
        NonAdminMember(
            id = 1,
            name = "Joel",
            tasks = emptyList<HomeTask>(),
            score = 0,
        ),
        NonAdminMember(
            id = 2,
            name = "Kaue",
            tasks = emptyList<HomeTask>(),
            score = 0,
        ),
    )
    CreateTaskBottomSheet(
        first = memberList.first(),
        members = memberList,
        {},
    )
}
