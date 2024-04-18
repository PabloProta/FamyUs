package com.famy.us.authentication

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.H6
import com.famy.us.core.ui.components.DefaultButton
import com.famy.us.core.ui.tertiary_600
import com.famy.us.core.ui.tertiary_900
import com.famy.us.core.ui.tertiary_main

@Composable
fun QrCodeScreen(
    popBackStack: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }
    QrCodeScreenBackGround {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.size(107.dp))
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(118.dp),
                color = tertiary_main,
                shape = CircleShape,
            ) {}
            Spacer(modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.weight(1f))
            BottomSheet(
                modifier = Modifier,
                onClickReadQR = {
                    // Read the qr code here.
                },
            )
        }
    }
}

@Composable
private fun BottomSheet(
    modifier: Modifier,
    onClickReadQR: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .heightIn(min = 316.dp, max = 512.dp)
            .fillMaxWidth(),
    ) {
        val contentRef = createRef()
        val imageRef = createRef()
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .constrainAs(imageRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(contentRef.top)
                    top.linkTo(contentRef.top)
                },
            painter = painterResource(
                id = R.drawable.qr_code_composition,
            ),
            contentDescription = null,
        )
        BottomSheetContent(
            modifier = Modifier
                .constrainAs(contentRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            onClickReadQR = onClickReadQR,
        )
    }
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier,
    onClickReadQR: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            )
            .safeContentPadding()
            .padding(24.dp),
    ) {
        Spacer(modifier = Modifier.size(120.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .zIndex(2f),
            text = "Acesse sua família pelo QrCode",
            style = H6,
            color = tertiary_900,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Aponte sua câmera para o QrCode disponibilizado pelo adiministrador da" +
                "familia",
            color = tertiary_600,
            style = BodySmallRegular,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.size(48.dp))
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onClickReadQR,
        ) {
            Text(
                text = "Ler QR Code",
                style = ButtonMedium,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun QrCodeScreenBackGround(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(
                id = com.famy.us.core.ui.R.drawable.background_top_composition,
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        content()
    }
}

@Preview(
    device = "id:pixel_3",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun QrCodeScreenPreview() {
    QrCodeScreen({})
}
