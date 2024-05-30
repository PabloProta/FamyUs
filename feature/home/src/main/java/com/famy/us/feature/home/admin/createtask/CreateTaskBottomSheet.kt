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
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.famy.us.core.ui.tertiary_600
import com.famy.us.core.ui.tertiary_900
import com.famy.us.core.utils.resources.IconResource
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember
import com.famy.us.feature.home.admin.createtask.pickers.DateDialogInput
import com.famy.us.feature.home.admin.createtask.pickers.TimePickerInput
import com.famy.us.home.R
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("LongMethod")
@Composable
fun CreateTaskBottomContainer(
    first: NonAdminMember,
    members: List<NonAdminMember>,
    onCreateTask: (HomeTask) -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showTimerPicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var currentDate: Calendar? by remember { mutableStateOf(null) }
    var memberSelected by remember { mutableStateOf(first) }
    Box {
        ModalBottomSheet(
            modifier = Modifier.wrapContentSize(),
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = Color.White,
            contentColor = Color.White,
        ) {
            CreateTaskBottomSheet(
                first = memberSelected,
                members = members,
                onClickDateInput = { showTimerPicker = true },
                onClickCreate = { taskName, taskDescription ->
                    val newTask = HomeTask(
                        id = 0,
                        name = taskName,
                        description = taskDescription,
                        position = 0,
                        point = 100,
                        finish = if (currentDate?.timeInMillis != null) {
                            Date(currentDate?.timeInMillis!!)
                        } else {
                            null
                        },
                        assigned = memberSelected,
                    )
                    onCreateTask(newTask)
                },
                currentDate = currentDate,
                onSelectMember = { memberSelected = it },
            )
        }
        if (showTimerPicker) {
            TimePickerInput(
                calendar = currentDate,
                onCancel = { showTimerPicker = false },
                onConfirm = {
                    currentDate = it
                    showTimerPicker = false
                    showDatePicker = true
                },
            )
        }
        if (showDatePicker && currentDate != null) {
            DateDialogInput(
                calendar = currentDate!!,
                onDismiss = { showDatePicker = false },
                onConfirm = {
                    currentDate = it
                    showDatePicker = false
                },
            )
        }
    }
}

@Composable
private fun CreateTaskBottomSheet(
    first: NonAdminMember,
    currentDate: Calendar? = null,
    members: List<NonAdminMember>,
    onClickCreate: (taskName: String, taskDescription: String) -> Unit,
    onSelectMember: (NonAdminMember) -> Unit,
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
            onSelectMember = onSelectMember,
        )
        Spacer(modifier = Modifier.size(16.dp))
        NameInput(
            value = taskName,
            onValueChange = { taskName = it },
        )
        Spacer(modifier = Modifier.size(8.dp))
        DescriptionInput(
            value = taskDescription,
            onValueChange = { taskDescription = it },
        )
        Spacer(modifier = Modifier.size(8.dp))
        DatePickerInput(
            dateProvider = { currentDate },
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
                onClickToCreate = { onClickCreate(taskName, taskDescription) },
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
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
        onClickDateInput = {},
        onClickCreate = { _, _ -> },
        onSelectMember = {},
    )
}
