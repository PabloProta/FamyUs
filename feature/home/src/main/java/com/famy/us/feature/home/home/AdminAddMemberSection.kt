package com.famy.us.feature.home.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallMedium
import com.famy.us.core.ui.ButtonLarge
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_main
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember
import com.famy.us.home.R

@Composable
fun AdminAddMemberSection(
    modifier: Modifier = Modifier,
    list: List<NonAdminMember>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "Seus filhos",
            style = ButtonLarge,
            color = tertiary_50,
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items(list) { member ->
                MemberHolder(memberName = member.name)
                Spacer(modifier = Modifier.size(8.dp))
            }
            item {
                AddMemberHolder(
                    onClick = {},
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

private val BorderColor = Color(0XFF453274)

@Composable
private fun AddMemberButton(
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .drawBehind {
                val stroke = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(
                            10.dp.toPx(),
                            12.dp.toPx(),
                        ),
                        0f,
                    ),
                )
                drawCircle(color = BorderColor, style = stroke)
            }
            .padding(12.dp),
        shape = CircleShape,
        color = Color.Transparent,
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.ic_rounded_plus,
            ),
            contentDescription = null,
            tint = BorderColor,
        )
    }
}

@Composable
private fun AddMemberHolder(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AddMemberButton(onClick = onClick)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Adicionar",
            textAlign = TextAlign.Center,
            style = BodySmallMedium,
            color = tertiary_50,
        )
    }
}

@Composable
private fun MemberHolder(
    memberName: String,
) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImagePlaceHolder()
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = memberName,
            textAlign = TextAlign.Center,
            style = BodySmallMedium,
            color = tertiary_50,
        )
    }
}

@Composable
private fun ImagePlaceHolder() {
    Surface(
        modifier = Modifier
            .size(56.dp),
        shape = CircleShape,
        color = tertiary_main,
        border = BorderStroke(width = 2.dp, color = BorderColor),
    ) {}
}

@Preview
@Composable
fun AdminAddMemberPreview() {
    val memberList = listOf(
        NonAdminMember(
            id = 0,
            name = "Kelly",
            tasks = emptyList<HomeTask>(),
            score = 0,
        ),
        NonAdminMember(
            id = 1,
            name = "Joel",
            tasks = emptyList<HomeTask>(),
            score = 0,
        ),
        NonAdminMember(
            id = 2,
            name = "Kaue",
            tasks = emptyList<HomeTask>(),
            score = 0,
        ),
    )

    AdminAddMemberSection(list = memberList)
}
