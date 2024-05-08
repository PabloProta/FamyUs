package com.famy.us.feature.home.admin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallRegular
import com.famy.us.core.ui.H7
import com.famy.us.core.ui.primary_main
import com.famy.us.core.ui.tertiary_300
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_main
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember
import com.famy.us.home.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

private val CardColor = Color(0XFF1B162A)
private val DividerColor = Color(0XFF221C36)

@Composable
fun AdminTaskContainer(
    modifier: Modifier = Modifier,
    task: HomeTask,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = CardColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            TaskDescriptions(task = task)
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                color = DividerColor,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                UserInfoContainer(member = task.assigned)
                Spacer(modifier = Modifier.weight(1f))
                TimeContainer(task = task)
            }
        }
    }
}

@Composable
private fun TimeContainer(task: HomeTask) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = null,
            tint = primary_main,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            text = formatHour(task.finish),
            style = BodySmallRegular,
            color = Color.White,
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun formatHour(date: Date?): String {
    if (date == null) return ""
    val hourFormat24 = SimpleDateFormat("H:mm")
    val hourFormat12 = SimpleDateFormat("h:mm a")

    val hour24 = hourFormat24.format(date)
    val hour12 = hourFormat12.format(date)

    val hour = if (hour12.endsWith("AM") || hour12.endsWith("PM")) hour12 else hour24

    return "$hour${if (hour12.endsWith("AM") || hour12.endsWith("PM")) "" else "h"}"
}

@Composable
private fun UserInfoContainer(member: NonAdminMember?) {
    if (member == null) return
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ImagePlaceHolder()
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = member.name,
            style = BodySmallRegular,
            color = Color.White,
        )
    }
}

@Composable
private fun ImagePlaceHolder() {
    Surface(
        modifier = Modifier.size(24.dp),
        shape = CircleShape,
        contentColor = tertiary_main,
    ) {}
}

@Composable
private fun TaskDescriptions(
    task: HomeTask,
) {
    Column {
        Text(
            text = task.name,
            style = H7,
            color = tertiary_50,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Box(
            modifier = Modifier
                .heightIn(min = 35.dp),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = task.description,
                style = BodySmallRegular,
                color = tertiary_300,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun AdminTaskPreview() {
    val member = NonAdminMember(
        id = 0,
        name = "Pablo",
        tasks = emptyList(),
        score = 0,
    )
    val task = HomeTask(
        id = 0,
        name = "lavar louça",
        description = "Você precisa lavar a louça pq se sabe que fede se vc qfdsffsdfdsfdsfdsfsfsdfds",
        position = 0,
        assigned = member,
        point = 0,
        start = null,
        finish = Date.from(Instant.now()),
    )
    AdminTaskContainer(task = task)
}
