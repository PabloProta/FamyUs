package com.famy.us.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.utils.resources.IconResource

@Composable
internal fun ItemMenu(
    iconProvider: () -> IconResource,
    isClicked: Boolean,
    onClickItem: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .padding(20.dp),
        onClick = onClickItem,
    ) {
        Image(
            painter = iconProvider().asPainterResource(),
            contentDescription = null,
            colorFilter = if (isClicked) {
                null
            } else {
                ColorFilter.tint(color = tertiary_300)
            },
        )
    }
}
