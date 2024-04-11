package com.famy.us.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Suppress("MagicNumber")
@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFA371EF),
    shadowColor: Color = Color(0XFF803DD1),
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val cornerRadius = 12.dp
    val padding = 4.dp
    Box(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .drawWithCache {
                onDrawBehind {
                    drawRoundRect(
                        topLeft = Offset(
                            x = 0f,
                            y = 0f,
                        ),
                        color = shadowColor,
                        size = Size(
                            width = size.width,
                            height = size.height,
                        ),
                        cornerRadius = CornerRadius(
                            x = cornerRadius.toPx(),
                            y = cornerRadius.toPx() + 2.dp.toPx(),
                        ),
                    )
                }
            }
            .padding(bottom = padding)
            .width(IntrinsicSize.Max),
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            shape = RoundedCornerShape(cornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
            ),
            onClick = onClick,
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun OutlinedGrayButtonPreview() {
    DefaultButton(
        modifier = Modifier,
        onClick = {},
    ) {
        Text(
            modifier = Modifier
                .padding(20.dp),
            text = "Cadastre-se",
        )
    }
}
