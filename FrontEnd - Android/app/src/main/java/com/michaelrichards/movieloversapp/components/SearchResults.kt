package com.michaelrichards.movieloversapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelrichards.movieloversapp.model.Actor
import com.michaelrichards.movieloversapp.model.Movie
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.michaelrichards.movieloversapp.R

val testMovie = Movie(
    title = "Avengers: Endgame",
    year = 2019,
    imdbId = "tt4154796",
    rank = 451,
    topActors = listOf(Actor("Robert Downey Jr"), Actor("Chris Evans")),
    aka = "Avengers: Endgame (2019) ",
    imdbUrl = "https://imdb.com/title/tt4154756",
    imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_.jpg",
    photoHeight = 1382,
    photoWidth = 2048
)

@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    movie: Movie = testMovie
) {
    Card(
        modifier = modifier.clickable {  },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp, pressedElevation = 2.dp),
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row {
            Column {
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    model = movie.imagePosterLink,
                    contentDescription = "${movie.title} ${stringResource(id = R.string.poster_image)}"
                )
            }
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = movie.title)
                LazyRow {
                    items(items = movie.topActors) { actors ->
                        Text(text = actors.name, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

        }

    }
}

@Preview
@Composable
fun prevSearchCard() {
    SearchResults(movie = testMovie)
}