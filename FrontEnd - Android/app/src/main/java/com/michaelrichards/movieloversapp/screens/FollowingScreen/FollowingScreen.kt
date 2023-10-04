package com.michaelrichards.movieloversapp.screens.FollowingScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.TopBar
import com.michaelrichards.movieloversapp.ui.theme.backgroundColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FollowingScreen(navController: NavController) {

    val tabItems: List<String> = listOf("Following", "Followers")

    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }

    val pageState = rememberPagerState {
        tabItems.size
    }

    var nameSearch by remember {
        mutableStateOf("")
    }

    LaunchedEffect(pageState.currentPage, pageState.isScrollInProgress) {
        if (!pageState.isScrollInProgress) {
            selectedItemIndex = pageState.currentPage
        }
    }

    LaunchedEffect(selectedItemIndex) {

        pageState.animateScrollToPage(selectedItemIndex)

    }

    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController, 2) }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding)
        ) {
            Column {
                TabRow(selectedTabIndex = selectedItemIndex) {
                    tabItems.forEachIndexed { index, item ->
                        Tab(
                            selected = selectedItemIndex == index,
                            onClick = { selectedItemIndex = index },
                            selectedContentColor = Color.DarkGray,
                            unselectedContentColor = Color.LightGray
                        ) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Gray),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(12.dp),
                                        text = item,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }

                            }

                        }
                    }
                }
                HorizontalPager(state = pageState) {
                    Surface(modifier = Modifier.fillMaxSize(), color = backgroundColor) {
                        Text(text = "Hello $it")
                    }
                }
            }


        }


    }
}


