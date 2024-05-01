package com.famy.us.feature.home.managed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallMedium
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.ButtonLarge
import com.famy.us.core.ui.primary_main
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_main
import com.famy.us.home.R

@Composable
fun ManagedTopBar(
    familyName: String,
    memberName: String,
    score: Int,
) {
    Surface(
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        color = tertiary_main.copy(alpha = 0.16f),
    ) {
        Row(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxWidth()
                .padding(
                    vertical = 9.dp,
                    horizontal = 24.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            ImagePlaceHolder()
            Spacer(modifier = Modifier.size(12.dp))
            Box(
                modifier = Modifier
                    .weight(1f),
            ) {
                TextsInputs(
                    memberName = memberName,
                    familyName = familyName,
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.CenterEnd,
            ) {
                NotificationContainer(
                    score = score,
                )
            }
        }
    }
}

private const val NumberLimit = 10000
private const val Thousand = 1000
private const val DotCount = 3

fun formatIntsWithDecimal(input: Int): String {
    var number = if (input >= NumberLimit) input / Thousand else input
    val inputString = number.toString()
    val length = inputString.length

    val result = StringBuilder()

    var i = length - 1
    var count = 0
    while (i >= 0) {
        result.append(inputString[i])
        count++
        if (count % DotCount == 0 && i != 0) {
            result.append('.')
        }
        i--
    }

    val appendK = if (input > NumberLimit) "K" else ""
    return result.reverse().toString() + appendK
}

@Composable
private fun TextsInputs(
    memberName: String,
    familyName: String,
) {
    Column {
        Text(
            text = "Hey, $memberName \uD83D\uDC4B\uD83C\uDFFC",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = ButtonLarge,
            color = tertiary_50,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "Família $familyName",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = BodySmallRegular,
            color = primary_main,
        )
    }
}

@Composable
private fun NotificationContainer(score: Int) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .background(
                color = Color.White.copy(alpha = 0.04f),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ScoreContainer(score = score)
        Spacer(modifier = Modifier.size(8.dp))
        VerticalDivider(
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.04f),
        )
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
private fun ScoreContainer(
    score: Int,
) {
    Image(
        painter = painterResource(id = R.drawable.img_coin),
        contentDescription = null,
    )
    Spacer(modifier = Modifier.size(4.dp))
    Text(
        modifier = Modifier
            .width(42.dp),
        text = formatIntsWithDecimal(score),
        style = BodySmallMedium,
        color = tertiary_50,
    )
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
internal fun ManagedTopBarPreview() {
    ManagedTopBar(
        familyName = "Prota",
        memberName = "Pablo",
        score = 9500,
    )
}
