package com.famy.us.feature.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.secondary_main
import com.famy.us.registration.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EnterFamilyBottomSheet(
    familyName: String,
    onClickEnter: () -> Unit,
    onClickQuit: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        modifier = Modifier
            .wrapContentSize(),
        onDismissRequest = onClickQuit,
        sheetState = sheetState,
    ) {
        EnterFamilyBottomSheetContent(
            sheetState = sheetState,
            familyName = familyName,
            onClickQuit = onClickQuit,
            onClickEnter = onClickEnter,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("MagicNumber")
@Composable
internal fun EnterFamilyBottomSheetContent(
    sheetState: SheetState,
    familyName: String,
    onClickQuit: () -> Unit,
    onClickEnter: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        IconContainer()
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = "Deseja entra na familia $familyName",
            style = H5,
            color = Color(0XFF393939),
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Você sera redirecionado para página home",
            style = BodySmallRegular,
            color = Color(0XFF727272),
        )
        Spacer(modifier = Modifier.size(48.dp))
        ButtonsContainer(
            onClickEnter = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    onClickEnter()
                }
            },
            onClickQuit = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    onClickQuit()
                }
            },
        )
    }
}

@Composable
@Suppress("MagicNumber")
internal fun ButtonsContainer(
    onClickEnter: () -> Unit,
    onClickQuit: () -> Unit,
) {
    DefaultButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClickEnter,
    ) {
        Text(
            text = "Entrar",
            style = ButtonMedium,
            color = Color.White,
        )
    }
    Spacer(modifier = Modifier.size(8.dp))
    OutlinedButton(
        shape = ButtonDefaults.outlinedShape,
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClickQuit,
    ) {
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = "Não Entrar",
            style = ButtonMedium,
            color = Color(0XFF716F8A),
        )
    }
    Spacer(modifier = Modifier.size(24.dp))
}

@Composable
internal fun IconContainer() {
    IconBackground {
        Image(
            modifier = Modifier
                .zIndex(1f),
            painter = painterResource(id = R.drawable.ic_help),
            contentDescription = null,
        )
    }
}

@Composable
internal fun IconBackground(
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        content()
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(120.dp),
            shape = CircleShape,
            color = secondary_main.copy(alpha = 0.08f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(93.dp),
            shape = CircleShape,
            color = secondary_main.copy(alpha = 0.16f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(70.dp),
            shape = CircleShape,
            color = secondary_main.copy(alpha = 0.32f),
        ) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0XFFFFFF,
)
@Composable
internal fun EnterFamilyBottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState()
    EnterFamilyBottomSheetContent(
        sheetState = sheetState,
        familyName = "Medeiros",
        onClickQuit = {},
        onClickEnter = {},
    )
}
