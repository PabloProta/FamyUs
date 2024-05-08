package com.famy.us.feature.home.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.famy.us.core.ui.dropShadow
import com.famy.us.core.ui.primary_main
import com.famy.us.core.ui.tertiary_50
import com.famy.us.core.utils.navigation.Destination
import com.famy.us.core.utils.resources.IconResource
import com.famy.us.feature.home.ItemMenu
import com.famy.us.feature.home.model.MenuItem
import com.famy.us.home.R

@Composable
fun AdminNavigationBar(
    modifier: Modifier = Modifier,
    menus: List<MenuItem>,
    onClickAdd: () -> Unit,
    onNavigateAt: (Destination) -> Unit,
) {
    val orderedMenus = menus.sortedBy { it.priority }
    val startMenus = orderedMenus.take(menus.size / 2)
    val endMenus = orderedMenus.drop(menus.size / 2)
    var currentItemClicked: MenuItem? by remember { mutableStateOf(null) }

    LazyRow(
        modifier = Modifier
            .safeDrawingPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(64.dp),
            ).then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        userScrollEnabled = false,
    ) {
        items(startMenus) { item ->
            ItemMenu(
                iconProvider = {
                    if (currentItemClicked == item) {
                        item.onSelectIcon
                    } else {
                        item.icon
                    }
                },
                isClicked = currentItemClicked == item,
                onClickItem = {
                    currentItemClicked = item
                    onNavigateAt(item.destination)
                },
            )
        }
        item {
            AddTaskButton(
                onClick = onClickAdd,
            )
        }

        items(endMenus) { item ->
            ItemMenu(
                iconProvider = {
                    if (currentItemClicked == item) {
                        item.onSelectIcon
                    } else {
                        item.icon
                    }
                },
                isClicked = currentItemClicked == item,
                onClickItem = {
                    currentItemClicked = item
                    onNavigateAt(item.destination)
                },
            )
        }
    }
}

@Composable
private fun AddTaskButton(
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .padding(12.dp)
            .background(
                shape = CircleShape,
                color = primary_main,
            )
            .dropShadow(
                color = primary_main.copy(alpha = 0.8f),
                blurRadius = 20.dp,
            ),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_rounded_plus),
            contentDescription = null,
            tint = tertiary_50,
        )
    }
}

private val tempDest = object : Destination("") {}

private val MenusPreview = listOf(
    MenuItem(
        name = "Home",
        destination = tempDest,
        priority = 0,
        icon = IconResource.fromImageVector(Icons.Rounded.Home),
        onSelectIcon = IconResource.fromImageVector(Icons.Rounded.Home),
        screen = {},
    ),
    MenuItem(
        name = "Note",
        destination = tempDest,
        priority = 1,
        icon = IconResource.fromImageVector(Icons.Rounded.Notes),
        onSelectIcon = IconResource.fromImageVector(Icons.Rounded.Notes),
        screen = {},
    ),
)

@Preview(
    device = "id:pixel_xl",
    showBackground = true,
    backgroundColor = 0XFFA371EF,
)
@Composable
internal fun AdminNavigationBarPreview() {
    AdminNavigationBar(
        menus = MenusPreview,
        onNavigateAt = {},
        onClickAdd = {},
    )
}
