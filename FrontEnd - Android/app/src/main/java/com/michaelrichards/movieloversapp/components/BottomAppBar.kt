package com.michaelrichards.movieloversapp.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.ui.theme.accentColor

@Composable
fun BottomBar(navController: NavController, itemNumber: Int) {

    var selectedTabIndex by remember {
        mutableIntStateOf(itemNumber)
    }

    BottomAppBar(
        containerColor = Color.DarkGray
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabItems[index].path.route){
                            popUpTo(Graphs.MainGraph.routeName){
                                inclusive = true
                            }
                        }
                    },
                    text = {
                        Text(text = item.title, color = accentColor)
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedTabIndex) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            tint = accentColor
                        )
                    }
                )
            }
        }
    }
}

private val tabItems: List<TabItem> = listOf(
    TabItem(
        title = "Home",
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        path = Screens.HomeScreen
    ),
    TabItem(
        title = "Search",
        unselectedIcon = Icons.Outlined.Search,
        selectedIcon = Icons.Filled.Search,
        path = Screens.SearchScreen
    ),
    TabItem(
        title = "Follow",
        unselectedIcon = Icons.Outlined.Groups,
        selectedIcon = Icons.Filled.Groups,
        path = Screens.FollowScreen
    ),
    TabItem(
        title = "Account",
        unselectedIcon = Icons.Outlined.AccountCircle,
        selectedIcon = Icons.Filled.AccountCircle,
        path = Screens.AccountScreen
    )
)

private data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val path: Screens
)