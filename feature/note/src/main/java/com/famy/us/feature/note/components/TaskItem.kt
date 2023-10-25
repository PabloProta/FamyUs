package com.famy.us.feature.note.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.domain.model.HomeTask

@Suppress("LongMethod", "MagicNumber")
@Composable
internal fun TaskItem(
    task: HomeTask,
    isDragged: Boolean,
    isSelected: Boolean,
    isSelecting: Boolean,
    verticalTranslation: Int,
    onClickCard: () -> Unit,
) {
    val zIndex = if (isDragged) 1.0f else 0.0f
    val elevation = if (isDragged) 8.dp else 0.dp
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        if (isSelecting) {
            RadioButton(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 32.dp)
                    .zIndex(Int.MAX_VALUE.toFloat()),
                selected = isSelected,
                onClick = onClickCard,
            )
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .size(148.dp)
                .padding(all = if (!isSelected) 0.dp else 18.dp)
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
                    onClickCard()
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
            isSelecting = true,
            isDragged = false,
            verticalTranslation = 0,
            isSelected = false,
        )
    }
}
