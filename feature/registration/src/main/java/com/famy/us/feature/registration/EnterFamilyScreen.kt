package com.famy.us.feature.registration

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.components.ContainerWithTopBar
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.components.DefaultTextField
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_400
import com.famy.us.core.ui.tertiary_50

@Composable
fun EnterFamilyScreen() {
    var familyCode by remember { mutableStateOf(TextFieldValue("")) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var expandContent by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }
    val familyName = "Antonini"
    val animateFinishContentSize by animateDpAsState(
        targetValue = if (!expandContent) {
            120.dp
        } else {
            6000.dp
        },
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (expandContent) {
            FinishFlowScreen(
                modifier = Modifier
                    .zIndex(1f),
                title = "Você entrou na familia $familyName",
                description = "Agora basta seguir para ver os detalhes dos controlados",
                animateFinishContentSize = animateFinishContentSize,
                onClickBack = { /*TODO*/ },
            )
        }
        EnterFamilyContent(
            familyName = familyName,
            familyCode = familyCode,
            onChangeFamilyCode = {
                familyCode = it
            },
            showBottomSheet = showBottomSheet,
            onClickEnter = {
                showBottomSheet = false
                isFinished = true
            },
            onClickQuit = {
                showBottomSheet = false
            },
        )
    }

    if (isFinished) {
        LaunchedEffect(key1 = isFinished) {
            expandContent = true
        }
    }
}

@Composable
internal fun EnterFamilyContent(
    familyName: String,
    showBottomSheet: Boolean,
    onClickEnter: () -> Unit,
    onClickQuit: () -> Unit,
    familyCode: TextFieldValue,
    onChangeFamilyCode: (TextFieldValue) -> Unit,
) {
    ContainerWithTopBar(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 24.dp),
        onClickBack = { /*TODO*/ },
    ) {
        Box {
            if (showBottomSheet) {
                EnterFamilyBottomSheet(
                    familyName = familyName,
                    onClickEnter = onClickEnter,
                    onClickQuit = onClickQuit,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Spacer(modifier = Modifier.size(32.dp))
                Text(
                    text = "Entre em uma família",
                    style = H5,
                    color = tertiary_50,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Insira a baixo o código recebido pelo anfitrião para entrar em uma família",
                    style = BodySmallRegular,
                    color = tertiary_300,
                )
                Spacer(modifier = Modifier.size(24.dp))
                FamilyCodeInputContainer(
                    value = familyCode,
                    onValueChange = onChangeFamilyCode,
                )
                Spacer(modifier = Modifier.weight(1f))
                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onClickQuit,
                ) {
                    Text(
                        text = "Avançar",
                        style = ButtonMedium,
                        color = Color.White,
                    )
                }
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}

@Composable
fun FamilyCodeInputContainer(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = "Código da família",
        leadingIcon = {
            Icon(
                painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_person),
                contentDescription = null,
                tint = tertiary_400,
            )
        },
    )
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun EnterFamilyScreenPreview() {
    EnterFamilyScreen()
}
