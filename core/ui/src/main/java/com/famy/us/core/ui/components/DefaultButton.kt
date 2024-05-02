package com.famy.us.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.ButtonLarge

@Suppress("MagicNumber")
@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFA371EF),
    shadowColor: Color = Color(0XFF803DD1),
    onClick: () -> Unit,
    cornerRadius: Dp = 12.dp,
    contentPaddingValues: PaddingValues = PaddingValues(8.dp),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
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

                    drawRoundRect(
                        topLeft = Offset(
                            x = 0f,
                            y = 0f,
                        ),
                        color = containerColor,
                        size = Size(
                            width = size.width,
                            height = size.height - 4.dp.toPx(),
                        ),
                        cornerRadius = CornerRadius(
                            x = cornerRadius.toPx(),
                            y = cornerRadius.toPx() + 2.dp.toPx(),
                        ),
                    )
                }
            }
            .width(IntrinsicSize.Max)
            .heightIn(min = ButtonDefaults.MinHeight)
            .padding(ButtonDefaults.ContentPadding)
            .padding(contentPaddingValues),
        contentAlignment = Alignment.TopCenter,
    ) {
        content()
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
            modifier = Modifier,
            text = "Cadastre-se",
            color = Color.White,
            style = ButtonLarge,
        )
    }
}
