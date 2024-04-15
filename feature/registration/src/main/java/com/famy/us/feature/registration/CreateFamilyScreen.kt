package com.famy.us.feature.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.components.ContainerWithTopBar
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.components.DefaultTextField
import com.famy.us.core.ui.components.TopAppBar
import com.famy.us.core.ui.primary_container
import com.famy.us.core.ui.tertiary_100
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_50
import com.famy.us.registration.R
import kotlinx.coroutines.delay

@Composable
fun CreateFamilyScreen() {
    var familyName by remember { mutableStateOf(TextFieldValue("")) }
    var isFinished by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ContainerWithTopBar(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            onClickBack = { /*TODO*/ },
        ) {
            CreateFamilyTextContainer()
            Spacer(modifier = Modifier.size(24.dp))
            FamilyNameInput(
                value = familyName,
                onValueChange = {
                    familyName = it
                },
            )
        }
        NormalButton(
            onClick = { isFinished = true },
            isFinishing = {
                isFinished
            },
        )
    }
}

@Suppress("MagicNumber")
@Composable
internal fun NormalButton(
    onClick: () -> Unit,
    isFinishing: () -> Boolean,
) {
    val animateSize by animateDpAsState(
        targetValue = if (isFinishing()) {
            120.dp
        } else {
            5000.dp
        },
    )

    var expandContent by remember { mutableStateOf(false) }
    val animateFinishContentSize by animateDpAsState(
        targetValue = if (!expandContent) {
            120.dp
        } else {
            6000.dp
        },
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box {
            if (expandContent) {
                FinishedFamilyCreationContainer(animateFinishContentSize)
            } else {
                DefaultButton(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .padding(horizontal = 24.dp)
                        .width(animateSize),
                    onClick = onClick,
                ) {
                    if (isFinishing()) {
                        Loader()
                    } else {
                        Text(
                            text = "Avançar",
                            style = ButtonMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
    }

    if (isFinishing()) {
        LaunchedEffect(key1 = isFinishing) {
            delay(2000)
            expandContent = true
        }
    }
}

@Suppress("MagicNumber")
@Composable
internal fun FinishedFamilyCreationContainer(
    animateFinishContentSize: Dp,
) {
    var visibility by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(primary_container)
            .size(animateFinishContentSize)
            .safeContentPadding(),
    ) {
        TopAppBar(withBackground = false, onClickBack = { /* TODO */ })
        AnimatedVisibility(
            visible = visibility,
            enter = scaleIn(
                animationSpec = tween(delayMillis = 400),
            ),
        ) {
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
                    text = "Cadastro Realizado com sucesso",
                    style = H5,
                    color = tertiary_50,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Voce será redirecionado para a página home",
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

@Composable
internal fun DoubleCheckContainer() {
    DoubleCheckBackground {
        Image(
            modifier = Modifier
                .zIndex(1f),
            painter = painterResource(id = R.drawable.ic_double_check_circle),
            contentDescription = null,
        )
    }
}

@Suppress("MagicNumber")
@Composable
internal fun DoubleCheckBackground(content: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        content()
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(120.dp),
            shape = CircleShape,
            color = Color(0XFFA371EF).copy(alpha = 0.08f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(93.dp),
            shape = CircleShape,
            color = Color(0XFFA371EF).copy(alpha = 0.16f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(70.dp),
            shape = CircleShape,
            color = Color(0XFFA371EF).copy(alpha = 0.32f),
        ) {}
    }
}

@Composable
internal fun Loader() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animation_loading),
    )
    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth(),
        composition = composition,
        iterations = LottieConstants.IterateForever,
        contentScale = ContentScale.Fit,
    )
}

@Composable
internal fun FamilyNameInput(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_person),
                contentDescription = null,
                tint = tertiary_300,
            )
        },
        label = "Nome da Familia",
    )
}

@Composable
internal fun CreateFamilyTextContainer() {
    Text(
        text = "Crie uma família",
        style = H5,
        color = tertiary_50,
    )
    Spacer(modifier = Modifier.size(4.dp))
    Text(
        text = "Escolha o nome que deseja dar para a familia",
        style = BodySmallRegular,
        color = tertiary_300,
    )
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun CreateFamilyScreenPreview() {
    CreateFamilyScreen()
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun FinishedCreationPreview() {
    FinishedFamilyCreationContainer(animateFinishContentSize = 4000.dp)
}
