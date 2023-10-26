package com.famy.us.feature.note.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectNoteOption(
    isAllCheckedProvider: () -> Boolean,
    onClickDone: () -> Unit,
    onClickDelete: () -> Unit,
    onCheckClicked: (isChecked: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 32.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(onClick = onClickDone) {
            Icon(imageVector = Icons.Rounded.Done, contentDescription = null)
        }
        IconButton(onClick = onClickDelete) {
            Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
        }
        Checkbox(
            checked = isAllCheckedProvider(),
            onCheckedChange = {
                onCheckClicked(it)
            },
        )
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun SelectNoteOptionPreview() {
    SelectNoteOption(
        isAllCheckedProvider = { true },
        onClickDone = { },
        onClickDelete = {},
        onCheckClicked = {},
    )
}
