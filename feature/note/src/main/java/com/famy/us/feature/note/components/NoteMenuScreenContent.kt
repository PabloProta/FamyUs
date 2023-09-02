package com.famy.us.feature.note.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun NoteMenuScreenContent(
    onAddTaskClicked: () -> Unit,
    onShowTasks: @Composable () -> Unit,
) {
    Box {
        onShowTasks()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                FloatingActionButton(
                    modifier = Modifier,
                    onClick = onAddTaskClicked,
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "")
                }
            }
        }
    }
}
