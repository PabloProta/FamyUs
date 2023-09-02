package com.famy.us.feature.note.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask

@OptIn(ExperimentalFoundationApi::class)
@Suppress("LongMethod", "MagicNumber")
@Composable
internal fun TaskItem(
    task: HomeTask,
    onClickCard: () -> Unit,
    onEditIsClicked: () -> Unit,
    onDeleteIsClicked: () -> Unit,
) {
    var showContent by remember {
        mutableStateOf(true)
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .size(148.dp)
            .padding(bottom = 8.dp)
            .combinedClickable(
                onClick = {
                    if (showContent) {
                        onClickCard()
                    }
                    showContent = true
                },
                onLongClick = {
                    showContent = false
                },
            ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        shape = RoundedCornerShape(50.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            if (showContent) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = task.name,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight(700),
                    ),
                )
                Text(
                    text = "pontos: ${task.point}",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Light,
                    ),
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
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
                    Spacer(modifier = Modifier.padding(8.dp))
                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(38.dp),
                        onClick = {
                            onDeleteIsClicked()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.secondary,
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Delete,
                            contentDescription = "edit ${task.name}",
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun TaskItemPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        TaskItem(
            task = HomeTask.Empty.copy(name = "Joares", point = 10),
            onClickCard = {},
            onEditIsClicked = {},
            onDeleteIsClicked = {},
        )
    }
}
