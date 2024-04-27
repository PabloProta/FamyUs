package com.famy.us.feature.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember

@Composable
internal fun AdminHomeScreen(
    memberList: List<NonAdminMember>,
) {
    var badgesList by remember { mutableStateOf(FilterBadgesLoader.load()) }

    fun updateBadge(badge: FilterBadges) {
        badgesList = badgesList.map {
            if (it.isSelected && it.badge != badge) {
                it.copy(isSelected = false)
            } else if (it.badge == badge) {
                it.copy(
                    isSelected = true,
                )
            } else {
                it
            }
        }
    }
    Column {
        AdminAddMemberSection(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            list = memberList,
        )
        Spacer(modifier = Modifier.size(12.dp))
        TaskStatusContainer(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 24.dp),
            onClickStatus = {
                updateBadge(it)
            },
        )
        TaskFilterBadges(
            modifier = Modifier
                .padding(start = 24.dp),
            badges = badgesList,
            onClickBadge = { badge ->
                updateBadge(badge)
            },
        )
    }
}

@Preview
@Composable
internal fun AdminHomePreview() {
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
    AdminHomeScreen(memberList = memberList)
}
