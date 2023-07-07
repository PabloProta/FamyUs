package com.famy.us.feature.note.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DeleteForever
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask

@Suppress("LongMethod", "MagicNumber")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskItem(
    task: HomeTask,
    onClickCard: () -> Unit,
    onEditIsClicked: () -> Unit,
    onDeleteIsClicked: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
        onClick = onClickCard,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .widthIn(0.dp, 140.dp),
                text = "Tarefa: ${task.name}",
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(
                modifier = Modifier.size(30.dp),
            )
            Text(
                text = "pontos: ${task.point}",
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(
                modifier = Modifier
                    .weight(1f),
            )
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(38.dp),
                onClick = onEditIsClicked,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.secondary,
                ),
            ) {
                Icon(
                    imageVector = Icons.Sharp.Edit,
                    contentDescription = "edit ${task.name}",
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.1f),
            )
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(38.dp),
                onClick = onDeleteIsClicked,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.secondary,
                ),
            ) {
                Icon(
                    imageVector = Icons.Sharp.DeleteForever,
                    contentDescription = "delete ${task.name}",
                )
            }
        }
    }
}
