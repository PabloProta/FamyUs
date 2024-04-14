package com.famy.us.feature.onboarding.opening

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.core.ui.H4
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.utils.navigation.Destination
import com.famy.us.feature.onboarding.navigation.OnBoardingNavigation
import com.famy.us.onboarding.R

@Composable
fun OpeningScreenContainer(onNavigateTo: (Destination) -> Unit) {
    OpeningScreen(onNavigateTo)
}

@Composable
internal fun OpeningScreen(onNavigateTo: (Destination) -> Unit) {
    OpeningScreenBackground {
        Column(
            modifier = Modifier
                .zIndex(2f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
            ) {
                Spacer(modifier = Modifier.size(96.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 7.dp),
                    text = "Sua família mais organizada com \n FamyUS",
                    style = H4,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.size(32.dp))
                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onNavigateTo(OnBoardingNavigation.Authentication)
                    },
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = "Começando",
                    )
                }
            }

            OpeningImageComposition()
        }
    }
}

@Composable
fun OpeningImageComposition() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter),
        ) {
            Spacer(modifier = Modifier.size(90.dp))
            Image(
                modifier = Modifier,
                painter = painterResource(
                    id = R.drawable.opening_screen_image_2_composition,
                ),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun OpeningScreenBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_opening),
                contentScale = ContentScale.FillBounds,
            ),
    ) {
        content()
        Image(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxHeight()
                .align(Alignment.BottomEnd),
            painter = painterResource(
                id = R.drawable.opening_screen_image_1_composition,
            ),
            contentDescription = null,
        )
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun OpeningScreenPreview() {
    OpeningScreen({})
}
