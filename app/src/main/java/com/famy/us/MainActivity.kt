package com.famy.us

import FamyUsTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.famy.us.feature.home.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            AppContainer()
        }
    }
}

@Composable
fun AppContainer() {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FamyUsTheme {
        AppContainer()
    }
}
