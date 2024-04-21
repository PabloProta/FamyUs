package com.famy.us.authentication.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.tertiary_600
import com.famy.us.core.ui.tertiary_900
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatedPasswordBottomSheet(
    onClickToLogin: () -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
    ) {
        UpdatedPasswordBottomSheetContent(
            sheetState = sheetState,
            onClickToLogin = onClickToLogin,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UpdatedPasswordBottomSheetContent(
    sheetState: SheetState,
    onClickToLogin: () -> Unit,
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
            text = "Senha alterada",
            style = H5,
            color = tertiary_900,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Parabéns! Sua senha foi alterada com sucesso",
            style = BodySmallRegular,
            color = tertiary_600,
        )
        Spacer(modifier = Modifier.size(48.dp))
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    onClickToLogin()
                }
            },
        ) {
            Text(
                text = "Login",
                style = ButtonMedium,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
internal fun IconContainer() {
    IconBackground {
        Image(
            modifier = Modifier
                .zIndex(1f),
            painter = painterResource(id = com.famy.us.core.ui.R.drawable.ic_double_check_circle),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = IconColor),
        )
    }
}

private val IconColor = Color(0XFF007419)

@Suppress("MagicNumber")
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
            color = IconColor.copy(alpha = 0.08f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(93.dp),
            shape = CircleShape,
            color = IconColor.copy(alpha = 0.16f),
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .size(70.dp),
            shape = CircleShape,
            color = IconColor.copy(alpha = 0.32f),
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
internal fun UpdatePasswordBottomPreview() {
    val sheetState = rememberModalBottomSheetState()
    UpdatedPasswordBottomSheetContent(
        sheetState = sheetState,
        onClickToLogin = {},
    )
}
