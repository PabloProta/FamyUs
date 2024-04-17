package com.famy.us.feature.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.components.TopAppBar
import com.famy.us.core.ui.primary_container
import com.famy.us.core.ui.tertiary_100
import com.famy.us.core.ui.tertiary_50
import com.famy.us.feature.registration.components.DoubleCheckContainer
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
@Composable
internal fun FinishFlowScreen(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    animateFinishContentSize: Dp,
    onClickBack: () -> Unit,
) {
    var visibility by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .background(primary_container)
            .size(animateFinishContentSize)
            .safeDrawingPadding(),
        contentAlignment = Alignment.TopStart,
    ) {
        AnimatedVisibility(
            visible = visibility,
            enter = scaleIn(
                animationSpec = tween(delayMillis = 400),
            ),
        ) {
            TopAppBar(withBackground = false, onClickBack = onClickBack)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DoubleCheckContainer()
                Spacer(Modifier.size(24.dp))
                Text(
                    text = title,
                    style = H5,
                    color = tertiary_50,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = description,
                    style = BodySmallRegular,
                    color = tertiary_100,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

    LaunchedEffect(key1 = !visibility) {
        delay(300)
        visibility = true
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun FinishFlowScreenPreview() {
    FinishFlowScreen(
        title = "Finalizando",
        description = "Essa é a descrição para este component",
        animateFinishContentSize = 4000.dp,
        onClickBack = {},
    )
}
