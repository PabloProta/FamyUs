package com.famy.us.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.core.ui.primary_700
import com.famy.us.core.ui.tertiary_main
import com.famy.us.core.utils.navigation.Navigator
import com.famy.us.invite.navigation.InviteScreenNavigation

@Composable
fun HomeMenuScreen(onNavigate: (Navigator) -> Unit) {
    ContainerBackground {
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
    device = "spec:width=1440px," + "height=2560px,dpi=640",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun HomeMenuScreenPreview() {
    HomeMenuScreen({})
}
