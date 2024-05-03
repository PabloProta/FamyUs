package com.famy.us.feature.home.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.famy.us.feature.home.home.FilterBadges
import com.famy.us.feature.home.home.FilterBadgesLoader
import com.famy.us.feature.home.home.TaskFilterBadges
import com.famy.us.feature.home.home.TaskStatusContainer
import java.time.Instant
import java.util.Date

@Composable
internal fun AdminHomeScreen(
    memberList: List<NonAdminMember>,
    taskList: List<HomeTask>,
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
        TaskList(list = taskList)
    }
}

@Composable
private fun TaskList(
    list: List<HomeTask>,
) {
    if (list.isEmpty()) return
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 24.dp),
    ) {
        items(list) { task ->
            AdminTaskContainer(task = task)
            Spacer(modifier = Modifier.size(8.dp))
        }
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
    val member = memberList.first()
    val tasks = listOf(
        HomeTask(
            id = 0,
            name = "lavar louça",
            description = "Você precisa lavar a louça pq se sabe que fede se vc ficar sem fala kCkdask",
            position = 0,
            assigned = member,
            point = 0,
            start = null,
            finish = Date.from(Instant.now()),
        ),
        HomeTask(
            id = 1,
            name = "ir no mercado",
            description = "Precisa comprar umas coisas no mercado",
            position = 0,
            assigned = member,
            point = 0,
            start = null,
            finish = Date.from(Instant.now()),
        ),
    )
    AdminHomeScreen(
        memberList = memberList,
        taskList = tasks,
    )
}
