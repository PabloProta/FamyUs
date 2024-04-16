package com.famy.us.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.famy.us.core.ui.H7
import com.famy.us.core.ui.primary_700
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_main

@Suppress("")
@Composable
fun ContainerWithTopBar(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    content: @Composable () -> Unit,
) {
    ContainerBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
        ) {
            TopAppBar(onClickBack = onClickBack)
            Column(modifier = modifier) {
                content()
            }
        }
    }
}

@Composable
fun TopAppBar(
    withBackground: Boolean = true,
    onClickBack: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (withBackground) tertiary_main.copy(alpha = 0.4f) else Color.Transparent,
                shape = RoundedCornerShape(
                    bottomStart = 12.dp,
                    bottomEnd = 12.dp,
                ),
            )
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(20.dp),
    ) {
        val titleRef = createRef()
        val arrowRef = createRef()
        IconButton(
            modifier = Modifier
                .constrainAs(arrowRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .size(48.dp),
            onClick = {
                onClickBack()
            },
        ) {
            Icon(
                modifier = Modifier
                    .padding(12.dp),
                imageVector = Icons.Outlined.ArrowBack,
                tint = tertiary_50,
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier
                .constrainAs(titleRef) {
                    top.linkTo(arrowRef.top)
                    bottom.linkTo(arrowRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "FamyUS",
            style = H7,
            color = tertiary_50,
            textAlign = TextAlign.Center,
        )
    }
}

private const val BlurThreshold = 1.26f

@Composable
internal fun ContainerBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(tertiary_main),
    ) {
        Surface(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .blur(320.dp)
                .graphicsLayer {
                    translationY = -size.maxDimension / BlurThreshold
                },
            shape = CircleShape,
            color = primary_700,
        ) {}
        content()
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun ContainerWithTopBarPreview() {
    ContainerWithTopBar(
        modifier = Modifier,
        onClickBack = {},
    ) {}
}
