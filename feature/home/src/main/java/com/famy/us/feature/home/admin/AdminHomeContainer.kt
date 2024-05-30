package com.famy.us.feature.home.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember
import com.famy.us.feature.home.admin.createtask.CreateTaskBottomContainer
import com.famy.us.feature.home.model.MenuItem

private val MemberList = listOf(
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

@Suppress("LongMethod")
@Composable
internal fun AdminHomeContainerScreen(
    familyName: String,
    memberName: String,
    menus: List<MenuItem>,
    role: String,
) {
    var showAddTaskBottomSheet by remember { mutableStateOf(false) }
    var navigationBarTop by remember { mutableStateOf(0f) }
    var homeContentBottom by remember { mutableStateOf(0f) }
    var taskList by remember { mutableStateOf(emptyList<HomeTask>()) }
    var conflictAt by remember { mutableStateOf(-1) }
    var taskCardHeight by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (showAddTaskBottomSheet) {
            CreateTaskBottomContainer(
                first = MemberList.first(),
                members = MemberList,
                onDismiss = { showAddTaskBottomSheet = false },
                onCreateTask = { newTask ->
                    showAddTaskBottomSheet = false
                    taskList = taskList + newTask
                },
            )
        }
        AdminTaskContainer(
            modifier = Modifier
                .onSizeChanged {
                    if (taskCardHeight == 0)
                        taskCardHeight = it.height
                }
                .alpha(0f),
            task = HomeTask.Empty
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AdminTopBar(
                familyName = familyName,
                memberName = memberName,
                role = role,
            )
            Spacer(modifier = Modifier.size(12.dp))
            AdminHomeScreen(
                modifier = Modifier
                    .wrapContentSize()
                    .onGloballyPositioned {
                        val rect = it.boundsInWindow()
                        homeContentBottom = rect.bottom
                    },
                navBarTopPosition = navigationBarTop,
                memberList = MemberList,
                taskList = {
                    taskList
                },
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            AdminNavigationBar(
                modifier = Modifier
                    .onGloballyPositioned {
                        val rect = it.boundsInWindow()
                        navigationBarTop = rect.top
                    },
                menus = menus,
                onClickAdd = {
                    showAddTaskBottomSheet = true
                },
                onNavigateAt = {},
            )
        }
    }
}

@Preview
@Composable
fun AdminHomeContainerPreview() {
    AdminHomeContainerScreen(
        familyName = "Prota",
        memberName = "Pablo",
        role = "Admin",
        menus = emptyList(),
    )
}
