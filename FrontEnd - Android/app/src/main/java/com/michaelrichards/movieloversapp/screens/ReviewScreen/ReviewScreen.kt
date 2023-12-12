package com.michaelrichards.movieloversapp.screens.ReviewScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.components.RatingBar
import com.michaelrichards.movieloversapp.dtos.ReviewMovieDTO
import com.michaelrichards.movieloversapp.model.FullMovie
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState

private const val TAG = "ReviewScreen"

@Composable
fun ReviewScreen(
    navController: NavController,
    viewModel: ReviewViewModel = hiltViewModel(),
    imdbId: String,
    close: () -> Unit
) {

    val movieDetails by viewModel.movieDetails.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(imdbId)
    }

    LaunchedEffect(viewModel.reviewSuccess) {
        if (viewModel.reviewSuccess.value) {
            Toast.makeText(context, "Review Submitted", Toast.LENGTH_LONG).show()
            navController.popBackStack()
        }
    }

    var rating = remember {
        mutableIntStateOf(0)
    }

    val review = remember {
        mutableStateOf("")
    }

    when (movieDetails) {
        is ApiSuccessFailState.BadRequest -> {

        }

        is ApiSuccessFailState.Loading -> {
            LinearProgressIndicator()
        }

        is ApiSuccessFailState.Success -> {
            movieDetails.data?.let { movie ->
                ReviewScreenLayout(
                    movie = movie,
                    rating = rating,
                    review = review,
                ) {
                    if (rating.intValue == 0){
                        Toast.makeText(context, "Rating Cannot be 0", Toast.LENGTH_SHORT).show()
                    }
                    else if (review.value.length < 10 || review.value.length >= 3000){
                        Toast.makeText(context, R.string.review_length_message, Toast.LENGTH_LONG)
                            .show()
                    }
                    else {
                        val movieReview =
                            movie.short.actor.map { it.name }.reduce { acc, s -> "$acc , $s" }?.let {
                                ReviewMovieDTO(
                                    imdbId = movie.imdbId,
                                    actors = it,
                                    description = review.value,
                                    imagePosterLink = movie.short.image,
                                    rating = rating.intValue,
                                    title = movie.short.name,
                                    imdbIdUrl = movie.short.url
                                )
                            }
                        if (movieReview != null) {
                            viewModel.reviewMovie(movieReview)
                        }

                        close()

                    }
                }
            }
        }
    }


}

@Composable
fun ReviewScreenLayout(
    movie: FullMovie,
    rating: MutableIntState,
    review: MutableState<String>,
    onSubmit: () -> Unit
) {


    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(Modifier.padding(8.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier.size(100.dp),
                    model = movie.short.image,
                    contentDescription = "${movie.short.name} ${stringResource(id = R.string.poster_image)}"
                )
                Text(text = movie.short.name)
            }

            Row {
                Text(text = stringResource(id = R.string.rate_movie))
                RatingBar(rating = rating.intValue) { newRating ->
                    rating.intValue = newRating
                }
            }
            Spacer(modifier = Modifier.height(16.dp))


            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(8.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
                value = review.value,
                onValueChange = { review.value = it },
                minLines = 5,
                maxLines = 10,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                decorationBox = { innerTextField ->
                    Box {
                        if (review.value.isEmpty()) {
                            Text(
                                text = "Right A Review",
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Button(onClick = {
                    onSubmit()
                }) {
                    Text(text = stringResource(id = R.string.submit_review))
                }
            }

        }


    }


}