package com.famy.us

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.famy.us.core.extensions.logD
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            AppContainer()
        }
    }

    @Composable
    fun AppContainer(
        navController: NavHostController = rememberNavController(),
    ) {
        NavHost(
            navController,
            startDestination = "test",
        ) {
            composable("test") {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    ElevatedButton(
                        onClick = {
                            navController.navigate("navigation")
                        },
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp),
                            text = "Click to navigate"
                        )
                    }
                }
            }

            composable(
                route = "navigation",
            ) { backstackEntry ->
                logD { "navigation" }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Another screen")
                }
            }
        }
    }
}
