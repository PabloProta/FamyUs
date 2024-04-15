package com.famy.us.feature.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.H5
import com.famy.us.core.ui.H7
import com.famy.us.core.ui.components.ContainerWithTopBar
import com.famy.us.core.ui.primary_main
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_main
import com.famy.us.registration.R

@Composable
fun CreatingAccountRouterScreen() {
    ContainerWithTopBar(
        modifier = Modifier.padding(horizontal = 24.dp),
        onClickBack = { /*TODO*/ },
    ) {
        Spacer(Modifier.size(32.dp))
        Text(
            text = "Boas vindas. Qual tipo de conta deseja criar?",
            style = H5,
            color = tertiary_50,
        )
        Spacer(Modifier.size(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            CreateFamilyOption(
                modifier = Modifier
                    .weight(1f),
                onClick = { /*TODO*/ },
            )
            Spacer(modifier = Modifier.size(8.dp))
            EnterFamilyOption(
                modifier = Modifier
                    .weight(1f),
                onClick = { /*TODO*/ },
            )
        }
    }
}

@Composable
internal fun CreateFamilyOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    CardCreationOption(
        modifier = modifier,
        painter = painterResource(id = R.drawable.img_big_family),
        text = "Criar uma familia",
        onClick = onClick,
    )
}

@Composable
internal fun EnterFamilyOption(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    CardCreationOption(
        modifier,
        painter = painterResource(id = R.drawable.img_small_family),
        text = "Monitorar família",
        onClick = onClick,
    )
}

@Composable
internal fun CardCreationOption(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min),
    ) {
        CardOptionContent(
            painter = painter,
            text = text,
            contentDescription = contentDescription,
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            IconButton(
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = primary_main,
                    )
                    .border(
                        width = 4.dp,
                        color = tertiary_main,
                        shape = CircleShape,
                    ),
                onClick = onClick,
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = tertiary_50,
                )
            }
        }
    }
}

@Composable
internal fun CardOptionContent(
    painter: Painter,
    contentDescription: String? = null,
    text: String,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                )
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.FillWidth,
                )
            }

            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = text,
                style = H7,
                color = tertiary_main,
            )
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
internal fun CreatingAccountRouterPreview() {
    CreatingAccountRouterScreen()
}
