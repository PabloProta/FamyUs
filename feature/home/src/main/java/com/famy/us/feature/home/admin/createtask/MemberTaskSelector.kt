package com.famy.us.feature.home.admin.createtask

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.BodySmallMedium
import com.famy.us.core.ui.tertiary_100
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.ui.tertiary_600
import com.famy.us.core.ui.tertiary_900
import com.famy.us.core.ui.tertiary_main
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember

@Composable
fun MemberTaskSelector(
    first: NonAdminMember,
    members: List<NonAdminMember>,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var memberSelected by remember { mutableStateOf(first) }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        MemberSelectedValue(
            member = memberSelected,
            isExpanded = isExpanded,
            onClick = {
                isExpanded = true
            },
        )
        DropdownMenu(
            modifier = Modifier
                .background(color = tertiary_50),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            },
        ) {
            members
                .filter { it != memberSelected }
                .forEach { member ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = {
                            MemberMenuItem(member = member)
                        },
                        onClick = {
                            memberSelected = member
                            isExpanded = false
                        },
                    )
                    if (members.last() != member) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                            color = tertiary_100,
                        )
                    }
                }
        }
    }
}

@Composable
private fun MemberMenuItem(
    member: NonAdminMember,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ImagePlaceHolder()
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            modifier = Modifier
                .weight(1f),
            text = member.name,
            style = BodySmallMedium,
            color = tertiary_900,
        )
        Spacer(modifier = Modifier.size(12.dp))
    }
}

@Composable
private fun MemberSelectedValue(
    member: NonAdminMember,
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(12.dp),
                color = tertiary_50,
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ImagePlaceHolder()
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            modifier = Modifier
                .weight(1f),
            text = member.name,
            style = BodySmallMedium,
            color = tertiary_900,
        )
        IconButton(
            onClick = onClick,
        ) {
            if (!isExpanded) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = tertiary_600,
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = null,
                    tint = tertiary_600,
                )
            }
        }
    }
}

private val BorderColor = Color(0XFF453274)

@Composable
private fun ImagePlaceHolder() {
    Surface(
        modifier = Modifier
            .size(40.dp),
        shape = CircleShape,
        color = tertiary_main,
        border = BorderStroke(width = 2.dp, color = BorderColor),
    ) {}
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
)
@Composable
internal fun MemberTaskSelectorPreview() {
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

    MemberTaskSelector(
        first = memberList.first(),
        members = memberList,
    )
}
