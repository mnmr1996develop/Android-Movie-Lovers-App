package com.michaelrichards.movieloversapp.screens.FollowingScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.TopBar
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.ui.theme.accentColor
import com.michaelrichards.movieloversapp.ui.theme.backgroundColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FollowingScreen(
    navController: NavController,
    viewModel: FollowingViewModel = hiltViewModel()
) {

    val tabItems: List<String> = listOf("Following", "Followers")

    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }

    val pageState = rememberPagerState {
        tabItems.size
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
                        when (selectedItemIndex) {
                            1 -> UserCardColumn(
                                state = viewModel.followingState
                            ) {
                                viewModel.loadMoreFollowing()
                            }

                            else -> UserCardColumn(
                                state = viewModel.followersState
                            ) {
                                viewModel.loadMoreFollowers()
                            }
                        }
                    }
                }

            }


        }


    }
}


@Composable
fun UserCard(
    userDataDTO: UserDataDTO
) {
    Surface {

    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = accentColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            Text(
                text = "${userDataDTO.firstName} ${userDataDTO.lastName}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "@${userDataDTO.username}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = userDataDTO.email)

        }
    }
}

@Composable
fun UserCardColumn(
    state: ScreenState,
    atEndOfColumn: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(5.dp)
    ) {
        items(state.items.size) { i ->
            if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                atEndOfColumn()
            }
            UserCard(state.items[i])
        }

        item {
            if (state.isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}



