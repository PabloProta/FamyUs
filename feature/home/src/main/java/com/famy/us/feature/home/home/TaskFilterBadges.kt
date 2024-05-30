package com.famy.us.feature.home.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.extensions.logD
import com.famy.us.core.ui.H7
import com.famy.us.core.ui.components.DefaultButton

@Composable
internal fun TaskFilterBadges(
    modifier: Modifier = Modifier,
    badges: List<FilterBadgesState>,
    onClickBadge: (FilterBadges) -> Unit,
) {
    val listState = rememberLazyListState()
    var indexSelected by remember { mutableIntStateOf(-1) }
    badges.forEachIndexed { index, filterBadgesState ->
        if (filterBadgesState.isSelected) {
            indexSelected = index
            logD { "setting index" }
        }
    }
    LazyRow(
        modifier = modifier,
        state = listState,
    ) {
        itemsIndexed(badges) { index, badge ->

            FilterBadge(
                modifier = Modifier
                    .padding(end = 8.dp),
                name = badge.name,
                isSelected = badge.isSelected,
                onClick = {
                    onClickBadge(badge.badge)
                },
            )
        }
    }

    LaunchedEffect(indexSelected) {
        if (indexSelected > -1) {
            logD { "scrolling to : $indexSelected" }
            listState.animateScrollToItem(indexSelected)
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun FilterBadge(
    modifier: Modifier = Modifier,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(ButtonDefaults.MinHeight),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (!isSelected) {
            OutlinedButton(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onClick,
            ) {
                Text(
                    text = name,
                    style = H7,
                    color = Color(0XFF716F8A),
                )
            }
        } else {
            DefaultButton(
                cornerRadius = 8.dp,
                onClick = onClick,
                contentPaddingValues = PaddingValues(2.dp),
            ) {
                Text(
                    text = name,
                    style = H7,
                    color = Color.White,
                )
            }
        }
    }
}

@Preview
@Composable
internal fun TaskFilterBadgePreview() {
    val badges = listOf(
        FilterBadgesState(
            name = "Tarefas de hoje",
            badge = FilterBadges.TODAY,
            isSelected = true,
        ),
        FilterBadgesState(
            name = "Concluído",
            badge = FilterBadges.COMPLETED,
        ),
        FilterBadgesState(
            name = "Late",
            badge = FilterBadges.LATE,
        ),
        FilterBadgesState(
            name = "In progress",
            badge = FilterBadges.IN_PROGRESS,
        ),
        FilterBadgesState(
            name = "Total",
            badge = FilterBadges.TOTAL,
        ),
    )
    TaskFilterBadges(
        badges = badges,
        onClickBadge = {},
    )
}
