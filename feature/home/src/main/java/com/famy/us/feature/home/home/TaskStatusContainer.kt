package com.famy.us.feature.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.H6
import com.famy.us.core.ui.H7
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.utils.resources.IconResource
import com.famy.us.home.R

@Composable
internal fun TaskStatusContainer() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "Tarefas",
            style = H6,
            color = tertiary_50,
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            columns = GridCells.Fixed(2),
        ) {
            item {
                StatusItem(
                    iconResource = IconResource.fromDrawableResource(R.drawable.ic_task_total),
                    quantity = 0,
                    description = "Total de tarefas",
                )
            }
            item {
                StatusItem(
                    iconResource = IconResource.fromDrawableResource(
                        R.drawable.ic_task_in_progress,
                    ),
                    quantity = 0,
                    description = "Em progresso",
                )
            }
            item {
                StatusItem(
                    iconResource = IconResource.fromDrawableResource(R.drawable.ic_task_completed),
                    quantity = 0,
                    description = "Completas",
                )
            }
            item {
                StatusItem(
                    iconResource = IconResource.fromDrawableResource(R.drawable.ic_task_late),
                    quantity = 0,
                    description = "Atrasadas",
                )
            }
        }
    }
}

@Composable
private fun StatusItem(
    modifier: Modifier = Modifier,
    iconResource: IconResource,
    quantity: Int,
    description: String,
) {
    Column(
        modifier = modifier
            .padding(
                bottom = 8.dp,
            )
            .padding(end = 8.dp)
            .background(
                color = Color.White.copy(alpha = 0.04f),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Image(painter = iconResource.asPainterResource(), contentDescription = null)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = quantity.toString(),
            style = H7,
            color = tertiary_50,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = description,
            style = BodySmallRegular,
            color = tertiary_300,
        )
    }
}

@Preview
@Composable
internal fun TaskStatusPreview() {
    TaskStatusContainer()
}
