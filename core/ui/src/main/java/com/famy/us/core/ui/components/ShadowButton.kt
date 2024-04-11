package com.famy.us.core.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.innerShadow

@Suppress("MagicNumber")
@Composable
fun ShadowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color = Color(0XFF803DD1),
    shadowColor: Color = Color(0XFF6228A9),
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier
            .innerShadow(
                shape = RoundedCornerShape(12.dp),
                color = shadowColor,
                offsetY = 5.dp,
            )
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
    ) {
        content()
    }
}

@Preview
@Composable
fun ShadowButtonPreview() {
    ShadowButton(onClick = {}) {
        Text(text = "Cadastre-se")
    }
}
