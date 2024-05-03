package com.famy.us.feature.home.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
internal fun AdminHomeContainerScreen(
    familyName: String,
    memberName: String,
    menus: List<MenuItem>,
    role: String,
) {
    var showAddTaskBottomSheet by remember { mutableStateOf(false) }

    var taskList by remember { mutableStateOf(emptyList<HomeTask>()) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (showAddTaskBottomSheet) {
            CreateTaskBottomContainer(
                first = MemberList.first(),
                members = MemberList,
                onDismiss = {
                    showAddTaskBottomSheet = false
                },
                onCreateTask = { newTask ->
                    showAddTaskBottomSheet = false
                    taskList = taskList + newTask
                },
            )
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            AdminNavigationBar(
                menus = menus,
                onClickAdd = {
                    showAddTaskBottomSheet = true
                },
                onNavigateAt = {},
            )
        }
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
                memberList = MemberList,
                taskList = taskList,
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
