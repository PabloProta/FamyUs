package com.famy.us.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.famy.us.feature.home.admin.AdminHomeContainerScreen
import com.famy.us.feature.home.managed.ManagedNavigationBar
import com.famy.us.feature.home.managed.ManagedTopBar
import com.famy.us.feature.home.model.MenuItem

@Composable
fun HomeMenuScreenContainer(
    menus: List<MenuItem>,
) {
    HomeMenuScreen(
        isAdmin = true,
        familyName = "Prota",
        memberName = "Kaue",
        role = "Admin",
        score = 4500,
        menus = menus,
    )
}

@Composable
private fun HomeMenuScreen(
    isAdmin: Boolean = false,
    menus: List<MenuItem>,
    familyName: String = "",
    memberName: String = "",
    role: String = "",
    score: Int = 0,
) {
    ContainerBackground {
        if (isAdmin) {
            AdminHomeContainerScreen(
                familyName = familyName,
                memberName = memberName,
                role = role,
                menus = menus,
            )
        } else {
            Column {
                Spacer(modifier = Modifier.weight(1f))
                ManagedNavigationBar(
                    menus = menus,
                    onNavigateAt = {},
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                ManagedTopBar(
                    familyName = familyName,
                    memberName = memberName,
                    score = score,
                )
            }
        }
    }
}

private const val BlurThreshold = 1.26f

@Composable
internal fun ContainerBackground(content: @Composable BoxScope.() -> Unit) {
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
        menus = emptyList(),
    )
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
        menus = emptyList(),
    )
}
