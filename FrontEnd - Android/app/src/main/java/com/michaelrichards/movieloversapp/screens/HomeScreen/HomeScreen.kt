package com.michaelrichards.movieloversapp.screens.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.ReviewColumn
import com.michaelrichards.movieloversapp.components.TopBar
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import com.michaelrichards.movieloversapp.ui.theme.accentColor
import com.michaelrichards.movieloversapp.ui.theme.backgroundColor

@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeViewModel = hiltViewModel()
) {
    if (viewModel.data.value.data == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "",
                modifier = Modifier.clickable {
                    viewModel.logout()
                    navController.navigate(Graphs.AuthGraph.routeName) {
                        popUpTo(
                            Graphs.MainGraph.routeName
                        ) {
                            inclusive = true
                        }
                    }
                })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(200.dp), color = accentColor, strokeWidth = 10.dp
                )

            }
        }

    } else {
        BasicHomeScreen(navController, viewModel)
    }
}


@Composable
private fun BasicHomeScreen(navController: NavController, viewModel: HomeViewModel) {

    val movieReviews by viewModel.movieReviews.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getReviewsOfFriends()
    }

    Scaffold(topBar = {
        viewModel.data.value.data?.let {
            TopBar(
                navController = navController
            )
        }
    },
        bottomBar = { BottomBar(navController = navController, 0) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                when (movieReviews) {
                    is ApiSuccessFailState.BadRequest -> TODO()
                    is ApiSuccessFailState.Loading -> {
                        LinearProgressIndicator()
                    }

                    is ApiSuccessFailState.Success -> {
                        LazyColumn {
                            movieReviews.data?.let {
                                items(it) { movieReview ->
                                    viewModel.data.value.data?.let {
                                        ReviewColumn(
                                            reviewMovieDTO = movieReview,
                                            onDownVoteIconClick = {
                                                viewModel.downVoteReview(movieReview)
                                            },
                                            onUpVoteIconClick = {
                                                viewModel.upVoteReview(movieReview)
                                            },
                                            onDeleteClick = {
                                                viewModel.deleteReview(movieReview)
                                            },
                                            onEditClick = {

                                            }
                                        ){

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


