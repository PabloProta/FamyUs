package com.famy.us.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.famy.us.core.utils.navigation.Navigator
import com.famy.us.invite.navigation.InviteScreenNavigation

@Composable
internal fun HomeMenuScreen(onNavigate: (Navigator) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),
    ) {
        Column {
            Text(text = "Menu Screen")
        }
        ElevatedButton(
            onClick = {
                onNavigate(Navigator.NavigateTo(InviteScreenNavigation.InviteScreen))
            },
        ) {
            Text(text = "Invite Screen")
        }
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
