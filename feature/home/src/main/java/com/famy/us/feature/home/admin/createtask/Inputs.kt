package com.famy.us.feature.home.admin.createtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.famy.us.core.ui.components.DefaultTextField
import com.famy.us.core.ui.tertiary_600
import com.famy.us.core.utils.resources.IconResource
import com.famy.us.home.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
internal fun NameInput(
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
internal fun DescriptionInput(
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
internal fun DatePickerInput(
    dateProvider: () -> Calendar?,
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
        label = formatDate(dateProvider()) ?: "Horário",
    )
}

private fun formatDate(currentDate: Calendar?): String? {
    if (currentDate == null) {
        return null
    }
    val dateFormat = SimpleDateFormat("dd MMMM 'às' HH:mm", Locale.getDefault())
    return dateFormat.format(currentDate.time)
}
