package com.famy.us.feature.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.model.NonAdminMember

@Composable
internal fun AdminHomeScreen(
    memberList: List<NonAdminMember>,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp),
    ) {
        AdminAddMemberSection(list = memberList)
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
    AdminAddMemberSection(list = memberList)
}
