package com.famy.us.feature.home.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.ButtonMedium
import com.famy.us.core.ui.primary_main
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
    modifier: Modifier = Modifier,
    memberList: List<NonAdminMember>,
    navBarTopPosition: Float = 0f,
    taskList: () -> List<HomeTask>,
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
    Column(
        modifier = modifier,
    ) {
        AdminAddMemberSection(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            list = memberList,
        )
        Spacer(
            modifier = Modifier
                .heightIn(max = 12.dp, min = 4.dp)
                .fillMaxHeight(),
        )
        TaskStatusContainer(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 24.dp),
            onClickStatus = { badge ->
                updateBadge(badge)
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
        TaskList(
            navBarTopPosition = navBarTopPosition,
            list = taskList(),
            onClickToSeeTasks = {
                // Navigate to task menu
            },
        )
    }
}

@Composable
private fun TaskList(
    navBarTopPosition: Float,
    list: List<HomeTask>,
    onClickToSeeTasks: () -> Unit,
) {
    var conflictAt by remember { mutableStateOf(-1) }
    if (list.isEmpty()) return
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 24.dp),
    ) {
        itemsIndexed(if (conflictAt < 0) list else list.take(conflictAt)) { index, task ->
            Column(
                modifier = Modifier
                    .onGloballyPositioned {
                        val root = it.boundsInRoot()
                        val cardBottom = root.bottom
                        if (cardBottom > navBarTopPosition) {
                            conflictAt = list.size - 1
                        }
                    },
            ) {
                if (index == 0) {
                    AdminTaskContainer(
                        modifier = Modifier
                            .alpha(if (conflictAt != -1) 0f else 1f),
                        task = task,
                    )
                } else {
                    AdminTaskContainer(
                        modifier = Modifier
                            .alpha(if (conflictAt != -1) 0f else 1f),
                        task = task,
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

        item {
            SeeAllTaskButton(onClickToSeeTasks = onClickToSeeTasks)
        }
    }
}

@Composable
private fun SeeAllTaskButton(
    onClickToSeeTasks: () -> Unit,
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = primary_main,
        ),
        border = BorderStroke(width = 1.dp, color = primary_main),
        onClick = onClickToSeeTasks,
    ) {
        Text(
            text = "Ver todas as tarefas",
            style = ButtonMedium,
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
        taskList = { tasks },
    )
}
