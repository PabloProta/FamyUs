package com.famy.us.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.primary_700
import com.famy.us.core.ui.tertiary_main
import com.famy.us.feature.home.topbar.AdminTopBar
import com.famy.us.feature.home.topbar.ManagedTopBar

@Composable
fun HomeMenuScreenContainer(
    content: @Composable () -> Unit,
) {
    HomeMenuScreen(
        isAdmin = false,
        familyName = "Prota",
        memberName = "Kaue",
        role = "Admin",
        score = 4500,
        content = content,
    )
}

@Composable
private fun HomeMenuScreen(
    isAdmin: Boolean = false,
    familyName: String = "",
    memberName: String = "",
    role: String = "",
    score: Int = 0,
    content: @Composable () -> Unit,
) {
    ContainerBackground {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (isAdmin) {
                AdminTopBar(
                    familyName = familyName,
                    memberName = memberName,
                    role = role,
                )
            } else {
                ManagedTopBar(
                    familyName = familyName,
                    memberName = memberName,
                    score = score,
                )
            }
            content()
        }
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
private fun HomeMenuAdminScreenPreview() {
    HomeMenuScreen(
        isAdmin = true,
        familyName = "Prota",
        memberName = "Pablo",
        role = "Root",
    ) {}
}

@Preview(
    device = "spec:width=1440px," + "height=2560px,dpi=640",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
private fun HomeMenuChildScreenPreview() {
    HomeMenuScreen(
        isAdmin = false,
        familyName = "Prota",
        memberName = "Kauê",
        role = "Controlado",
    ) {}
}
