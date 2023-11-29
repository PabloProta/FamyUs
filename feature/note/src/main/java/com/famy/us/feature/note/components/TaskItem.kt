package com.famy.us.feature.note.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.domain.model.HomeTask

@Suppress("LongMethod", "MagicNumber")
@Composable
internal fun TaskItem(
    task: HomeTask,
    isReordering: Boolean,
    isSelected: Boolean,
    isSelecting: Boolean,
    onClickCard: () -> Unit,
    onLongPress: () -> Unit,
) {
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
                .padding(all = if (isSelected && !isReordering) 18.dp else 0.dp)
                .padding(bottom = 8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onClickCard()
                        },
                        onLongPress = {
                            if (!isReordering) {
                                onLongPress()
                            }
                        },
                    )
                },
            elevation = CardDefaults.elevatedCardElevation(
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
            isSelected = false,
            isReordering = false,
            onLongPress = {},
        )
    }
}
