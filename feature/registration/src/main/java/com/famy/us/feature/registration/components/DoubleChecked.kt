package com.famy.us.feature.registration.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.registration.R

@Composable
internal fun DoubleCheckContainer() {
    DoubleCheckBackground {
        Image(
            modifier = Modifier
                .zIndex(1f),
            painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_double_check_circle),
            contentDescription = null,
        )
    }
}

@Suppress("MagicNumber")
@Composable
internal fun DoubleCheckBackground(content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        content()
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(120.dp),
            shape = CircleShape,
            color = Color(0XFFA371EF).copy(alpha = 0.08f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(93.dp),
            shape = CircleShape,
            color = Color(0XFFA371EF).copy(alpha = 0.16f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(70.dp),
            shape = CircleShape,
            color = Color(0XFFA371EF).copy(alpha = 0.32f),
        ) {}
    }
}
