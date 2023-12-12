package com.michaelrichards.movieloversapp.screens.UserDetailsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.components.LogoutButton
import com.michaelrichards.movieloversapp.components.ReviewColumn
import com.michaelrichards.movieloversapp.components.TopBar
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.repositories.results.ApiState
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import com.michaelrichards.movieloversapp.repositories.results.AuthResult


@Composable
fun UserDetailsScreen(
    navController: NavController,
    username: String,
    viewModel: UserDetailsViewModel = hiltViewModel()
) {

    val userState by viewModel.user.collectAsState()
    val loggedIn by viewModel.logedIN.collectAsState()
    val context = LocalContext.current

    val userMovieReviews by viewModel.movieReviews.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserProfileDetails(username)

    }

    Scaffold(
        topBar = { TopBar(navController = navController, includeBackButton = true) },
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (userState) {
                is ApiState.Loading -> {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        LinearProgressIndicator()
                    }
                }

                is ApiState.Ok -> {
                    userState.data?.let {
                        BasicUserProfile(
                            userProfileDTO = it,
                            movieReview = userMovieReviews
                        )
                    }
                }

                else -> {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column {

                            LogoutButton {
                                viewModel.logout()
                                when (loggedIn) {
                                    is AuthResult.UnAuthorized -> {
                                        Toast.makeText(
                                            context,
                                            R.string.logout_successful,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate(Graphs.AuthGraph.routeName) {
                                            popUpTo(
                                                Graphs.MainGraph.routeName
                                            ) {
                                                inclusive = true
                                            }
                                        }
                                    }

                                    else -> {
                                        Toast.makeText(
                                            context,
                                            R.string.logout_unsuccessful,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                            Button(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.back),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }

                    }

                }


            }
        }
    }


}


@Composable
fun BasicUserProfile(
    userProfileDTO: UserProfileDTO,
    movieReview: ApiSuccessFailState<List<MovieReviewDataDTO>>
) {

    val following by remember {
        mutableStateOf(
            userProfileDTO.amIFollowing
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(text = "${userProfileDTO.firstName} ${userProfileDTO.lastName}")
                    Text(text = "@${userProfileDTO.username}")
                }


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${userProfileDTO.totalReviews}")
                    Text(text = stringResource(id = R.string.reviews))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${userProfileDTO.followers}")
                    Text(text = stringResource(id = R.string.followers))
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${userProfileDTO.following}")
                    Text(text = stringResource(id = R.string.following))
                }
            }
            Row {
                OutlinedButton(
                    onClick = {

                    }, modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                ) {
                    Text(text = stringResource(id = if (following) R.string.unfollow else R.string.follow))
                }
            }


            when (movieReview) {
                is ApiSuccessFailState.Loading -> CircularProgressIndicator()
                is ApiSuccessFailState.BadRequest -> CircularProgressIndicator()
                is ApiSuccessFailState.Success -> {
                    LazyColumn {
                        /*items(movieReview.data) { review ->
                            ReviewColumn(review, userProfileDTO) {

                            }

                        }*/
                        movieReview.data?.let {
                            items(it) { review ->
                                ReviewColumn(
                                    review,
                                    onDownVoteIconClick = {},
                                    onUpVoteIconClick = {},
                                    onEditClick = {},
                                    onDeleteClick = {}
                                    ) {

                                }
                                Divider(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            }

        }
    }
}


