package com.michaelrichards.movieloversapp.screens.SearchScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.MovieSearchResults
import com.michaelrichards.movieloversapp.components.TopBar
import com.michaelrichards.movieloversapp.components.UserSearchResults
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.ui.theme.accentColor

private const val TAG = "SearchScreen"

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController = NavController(LocalContext.current),
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchString by viewModel.searchText.collectAsState()

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val isSearching by viewModel.isSearching.collectAsState()

    val users by viewModel.users.collectAsState()

    val movies by viewModel.movies.collectAsState()


    val tabs = listOf(
        TabItem(
            tabName = "Movie",
            selectedIcon = Icons.Filled.Movie,
            unselectedIcon = Icons.Outlined.Movie,
            data = viewModel.movies.collectAsState().value
        ) {
            viewModel.searchMovie(searchString)
        },
        TabItem(
            tabName = "User",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            data = viewModel.users.collectAsState().value
        ) {
            viewModel.searchUser(searchString)
        }
    )


    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState { tabs.size }

    val keyboard = LocalSoftwareKeyboardController.current




    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController, itemNumber = 1) }
    ) { paddingValues ->
        Surface(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
                tabs[selectedTabIndex].invokeAction()
            }

            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                    tabs[selectedTabIndex].invokeAction()
                }
            }

            Column(
                modifier = Modifier.padding(8.dp)
            ) {

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchString,
                    onValueChange = viewModel::onSearchTextChange,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = stringResource(
                                id = if (selectedTabIndex == 0) R.string.search_for_movie else R.string.search_for_user
                            )
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.secondary
                    ),
                    label = { Text(text = stringResource(id = if (selectedTabIndex == 0) R.string.search_for_movie else R.string.search_for_user)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {
                        tabs[selectedTabIndex].invokeAction()
                        keyboard?.hide()
                    })
                )

                Spacer(modifier = Modifier.height(8.dp))

                TabRow(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp)),
                    selectedTabIndex = selectedTabIndex,
                    divider = { Divider() },
                    containerColor = Color(0xFF333333)
                ) {
                    tabs.forEachIndexed { index: Int, tabName ->
                        Tab(
                            modifier = Modifier.clip(
                                when (index) {
                                    0 -> RoundedCornerShape(
                                        topStart = 30.dp,
                                        bottomStart = 30.dp,
                                        bottomEnd = 0.dp,
                                        topEnd = 0.dp
                                    )

                                    tabs.size - 1 -> RoundedCornerShape(
                                        topStart = 0.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 30.dp,
                                        topEnd = 30.dp
                                    )

                                    else -> RectangleShape
                                }
                            ),
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = tabName.tabName,
                                    color = if (selectedTabIndex == index) accentColor else Color.White
                                )
                            },
                            selectedContentColor = accentColor,
                            unselectedContentColor = Color.Gray,
                            icon = {
                                Icon(
                                    imageVector = if (selectedTabIndex == index) tabName.selectedIcon else tabName.unselectedIcon,
                                    contentDescription = ""
                                )
                            }
                        )
                    }
                }
                if (isSearching) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                } else {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) { index ->

                        when (selectedTabIndex) {
                            0 -> {
                                LazyColumn {
                                    items(movies) {
                                        MovieSearchResults(description = it){
                                            navController.navigate("${Screens.MovieDetails.route}/${it.imdbId}")
                                        }
                                    }
                                }
                            }

                            1 -> LazyColumn {
                                items(users) {
                                    Surface(
                                        modifier = Modifier.clickable {
                                            navController.navigate("${Screens.UserDetailsScreen.route}/${it.username}")
                                        }
                                    ) {
                                        var amIFollowing = remember {
                                            mutableStateOf(it.amIFollowing)
                                        }

                                        UserSearchResults(userDataDTO = it, following = amIFollowing) {
                                            if (it.amIFollowing) {
                                                viewModel.unfollow(it.username)

                                            } else {
                                                viewModel.follow(it.username)
                                            }
                                            amIFollowing.value = !amIFollowing.value
                                        }
                                    }

                                }
                            }
                        }

                    }
                }


            }
        }

    }


}

private data class TabItem(
    val tabName: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val data: List<Any>,
    val action: () -> Unit
) {
    fun invokeAction() {
        action()
    }

}

