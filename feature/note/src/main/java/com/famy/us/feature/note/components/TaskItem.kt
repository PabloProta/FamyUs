package com.famy.us.feature.note.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.core.extensions.logD
import com.famy.us.domain.model.HomeTask

@Suppress("LongMethod", "MagicNumber")
@Composable
internal fun TaskItem(
    task: HomeTask,
    isDragged: Boolean,
    showContentProvider: () -> Boolean,
    verticalTranslation: Int,
    onClickCard: (showingContent: Boolean) -> Unit,
    onEditIsClicked: () -> Unit,
    onDeleteIsClicked: () -> Unit,
) {

    val showContent = showContentProvider()
    val zIndex = if (isDragged) 1.0f else 0.0f
    val elevation = if (isDragged) 8.dp else 0.dp

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .size(148.dp)
            .padding(bottom = 8.dp)
            .zIndex(zIndex)
            .offset {
                if (isDragged) {
                    IntOffset(0, verticalTranslation)
                } else {
                    IntOffset(0, 0)
                }
            }
            .clickable {
                onClickCard(showContent)
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation,
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
            showContentProvider = { true },
            isDragged = false,
            onEditIsClicked = {},
            verticalTranslation = 0,
            onDeleteIsClicked = {},
        )
    }
}
