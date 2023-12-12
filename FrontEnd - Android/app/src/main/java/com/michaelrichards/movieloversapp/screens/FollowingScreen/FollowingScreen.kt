package com.michaelrichards.movieloversapp.screens.FollowingScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.TopBar
import com.michaelrichards.movieloversapp.components.UserSearchResults
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.ui.theme.accentColor
import com.michaelrichards.movieloversapp.utils.ScrollScreenState

private const val TAG = "FollowingScreen"
@OptIn(ExperimentalFoundationApi::class)
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
                            selectedContentColor = Color.Black,
                            unselectedContentColor = Color.White
                        ) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(12.dp),
                                        text = item,
                                        style = MaterialTheme.typography.bodyLarge.copy(color = accentColor, fontWeight = FontWeight.Bold),
                                    )
                                }

                            }

                        }
                    }
                }

                HorizontalPager(state = pageState) {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        when (selectedItemIndex) {
                            0 -> {
                                LaunchedEffect(Unit) {
                                    viewModel.resetFollowers()
                                }
                                UserCardColumn(
                                    state = viewModel.followingState,
                                    viewModel = viewModel,
                                    navController = navController

                                ) {
                                    viewModel.loadMoreFollowing()
                                }
                            }


                            else -> {
                                LaunchedEffect(Unit) {
                                    viewModel.resetFollowing()
                                }
                                UserCardColumn(
                                    state = viewModel.followersState,
                                    viewModel = viewModel,
                                    navController = navController
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
}


@Composable
private fun UserCardColumn(
    state: ScrollScreenState<UserProfileDTO>,
    viewModel: FollowingViewModel,
    navController: NavController,
    atEndOfColumn: () -> Unit

) {
    LazyColumn(
        contentPadding = PaddingValues(5.dp)
    ) {
        items(state.items.size) { i ->
            if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                atEndOfColumn()
            }

            val following = remember {
                mutableStateOf(state.items[i].amIFollowing)
            }
            Surface (
                modifier = Modifier.clickable { navController.navigate("${Screens.UserDetailsScreen.route}/${state.items[i].username}") }
            ){
                UserSearchResults(
                    userDataDTO = state.items[i],
                    following = following
                ) {
                    if (following.value) {
                        viewModel.unfollow(state.items[i].username)
                    } else
                        viewModel.follow(state.items[i].username)

                    following.value = !following.value
                }
            }

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



