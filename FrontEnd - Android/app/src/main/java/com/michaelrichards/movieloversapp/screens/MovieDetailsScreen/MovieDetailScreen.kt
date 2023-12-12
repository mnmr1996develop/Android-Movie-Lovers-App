package com.michaelrichards.movieloversapp.screens.MovieDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.model.FullMovie
import com.michaelrichards.movieloversapp.repositories.results.ApiSuccessFailState
import com.michaelrichards.movieloversapp.screens.ReviewScreen.ReviewScreen
import com.michaelrichards.movieloversapp.ui.theme.accentColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    imbdId: String,
    navController: NavController,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val movieDetails by viewModel.movieDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(imbdId)
    }

    val sheetState = rememberModalBottomSheetState()

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }


    Scaffold { paddingValues ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            when (movieDetails) {
                is ApiSuccessFailState.BadRequest -> {
                    Text(text = "Couldn't Find Movie")
                }

                is ApiSuccessFailState.Loading -> {
                    LinearProgressIndicator()
                }

                is ApiSuccessFailState.Success -> {
                    movieDetails.data?.let {
                        MovieDetailsLayout(movieDetails = it) {
                            isSheetOpen = true
                        }
                        if (isSheetOpen) {
                            ModalBottomSheet(
                                onDismissRequest = { isSheetOpen = false },
                                sheetState = sheetState
                            ) {
                                ReviewScreen(navController = navController, imdbId = imbdId) {
                                    isSheetOpen = false
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
fun MovieDetailsLayout(movieDetails: FullMovie, onClick: () -> Unit) {

    Column(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier.height(400.dp), horizontalArrangement = Arrangement.Center) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = movieDetails.short.image,
                contentDescription = "${movieDetails.short.name} ${stringResource(id = R.string.poster_image)}"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = movieDetails.short.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = accentColor,
                fontWeight = FontWeight.Bold
            )
        )



        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.starring), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            LazyRow {

                items(movieDetails.short.actor) { actorName ->
                    actorName.name?.let {
                        Text(
                            text = it, modifier = Modifier
                                .background(
                                    Color.Gray
                                )
                                .padding(10.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            color = accentColor

                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        movieDetails.short.director?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.directed_by), fontWeight = FontWeight.Bold)
                LazyRow {
                    items(it) { directorName ->
                        directorName.name?.let {
                            Text(
                                text = it, modifier = Modifier
                                    .background(
                                        Color.Gray
                                    )
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                color = accentColor

                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        movieDetails.short.creator?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.created_by), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                LazyRow {
                    items(it) { creatorName ->
                        creatorName.name?.let {
                            Text(
                                text = it, modifier = Modifier
                                    .background(
                                        Color.Gray
                                    )
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                color = accentColor

                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }



        Column(modifier = Modifier.padding(8.dp)) {

            movieDetails.short.contentRating?.let { contentRating ->
                Row {
                    Text(text = stringResource(id = R.string.content_rating))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = contentRating)
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
            movieDetails.short.datePublished?.let { datePublished ->
                Row {
                    Text(text = stringResource(id = R.string.released_date))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = datePublished)

                }

            }
        }

        movieDetails.top.plot?.let { Text(text = it.plotText.plainText) }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onClick() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = stringResource(id = R.string.review))
        }

    }
}