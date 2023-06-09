package com.famy.us.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeMenuScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),
    ) {
        Text(text = "Menu Screen")
    }
}
