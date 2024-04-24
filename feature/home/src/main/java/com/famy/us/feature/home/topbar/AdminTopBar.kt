package com.famy.us.feature.home.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonLarge
import com.famy.us.core.ui.primary_main
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_main
import com.famy.us.home.R

@Suppress("MagicNumber")
@Composable
fun AdminTopBar(
    familyName: String,
    memberName: String,
    role: String,
) {
    Surface(
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        color = tertiary_main.copy(alpha = 0.16f),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .padding(
                    vertical = 9.dp,
                    horizontal = 24.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImagePlaceHolder()
            Spacer(modifier = Modifier.size(12.dp))
            Box(
                modifier = Modifier.weight(3f),
            ) {
                TextsInputs(
                    memberName = memberName,
                    familyName = familyName,
                    role = role,
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            NotificationContainer()
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun TextsInputs(
    memberName: String,
    familyName: String,
    role: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Hey, $memberName \uD83D\uDC4B\uD83C\uDFFC",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = ButtonLarge,
            color = tertiary_50,
        )
        Spacer(modifier = Modifier.size(4.dp))
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val familyRef = createRef()
            val roleRef = createRef()
            Text(
                modifier = Modifier
                    .constrainAs(familyRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                text = "Família ${familyName.take(20)}",
                softWrap = false,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = BodySmallRegular,
                color = primary_main,
            )
            Text(
                modifier = Modifier
                    .constrainAs(roleRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                text = " • $role",
                maxLines = 1,
                style = BodySmallRegular,
                color = primary_main,
            )
            createHorizontalChain(familyRef, roleRef, chainStyle = ChainStyle.Packed(0f))
        }
    }
}

@Composable
private fun NotificationContainer() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.04f),
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp),
            painter = painterResource(
                id = R.drawable.ic_notification,
            ),
            contentDescription = null,
        )
    }
}

@Composable
private fun ImagePlaceHolder() {
    Surface(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape),
        color = tertiary_main,
        shape = CircleShape,
    ) {
        // Put the image here.
    }
}

@Preview
@Composable
internal fun AdminTopBarPreview() {
    AdminTopBar(
        familyName = "Prota",
        memberName = "Pablo",
        role = "Root",
    )
}
