package com.michaelrichards.movieloversapp.screens.UserDetailsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.dtos.MovieReviewDTO
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.ui.theme.accentColor


@Composable
fun UserDetailsScreen(
    navController: NavController,
    username: String,
) {

    val userMovieReviews = listOf<MovieReviewDTO>(
        MovieReviewDTO(
            imdbId = "tt4154796",
            rating = 8,
            description = "I mean it's Alright",
            imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
            actors = "Robert Downey Jr., Chris Evans",
            imdbIdUrl = "https://imdb.com/title/tt4154796",
            title = "Avengers: Endgame"
        ),
        MovieReviewDTO(
            imdbId = "tt4154796",
            rating = 7,
            description = "I mean it's Alright",
            imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
            actors = "Robert Downey Jr., Chris Evans",
            imdbIdUrl = "https://imdb.com/title/tt4154796",
            title = "Avengers: Endgame"
        ),
        MovieReviewDTO(
            imdbId = "tt4154796",
            rating = 7,
            description = "I mean it's Alright",
            imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
            actors = "Robert Downey Jr., Chris Evans",
            imdbIdUrl = "https://imdb.com/title/tt4154796",
            title = "Avengers: Endgame"
        ),
        MovieReviewDTO(
            imdbId = "tt4154796",
            rating = 7,
            description = "I mean it's Alright",
            imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
            actors = "Robert Downey Jr., Chris Evans",
            imdbIdUrl = "https://imdb.com/title/tt4154796",
            title = "Avengers: Endgame"
        ),
        MovieReviewDTO(
            imdbId = "tt4154796",
            rating = 7,
            description = "I mean it's Alright",
            imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
            actors = "Robert Downey Jr., Chris Evans",
            imdbIdUrl = "https://imdb.com/title/tt4154796",
            title = "Avengers: Endgame"
        )
    )

    val userProfileDTO = UserProfileDTO(
        firstName = "First",
        lastName = "Last",
        email = "email@email.com",
        username = "username",
        amIFollowing = true,
        followers = 27,
        following = 5,
        birthday = "01-01-2000",
        followingUser = true,
        totalReviews = 7
    )


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp))
        {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                Column {
                    Text(text = "${userProfileDTO.firstName} ${userProfileDTO.lastName}")
                    Text(text = "@${userProfileDTO.username}")
                }


                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${userProfileDTO.totalReviews}")
                    Text(text = "Reviews")
                }

                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "${userProfileDTO.followers}")
                    Text(text = "Followers")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${userProfileDTO.following}")
                    Text(text = "Following")
                }
            }
            Row {
                OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)) {
                    Text(text = "Follow")
                }
            }

            LazyColumn{
                items(userMovieReviews){review ->
                    ReviewColumn(review, userProfileDTO)
                    Divider(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}


@Preview
@Composable
fun prevDetailsScreen(
) {
    UserDetailsScreen(navController = NavController(LocalContext.current), username ="username")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ReviewColumn(
    movieReviewDTO: MovieReviewDTO = MovieReviewDTO(
        imdbId = "tt4154796",
        rating = 7,
        description = "I mean it's Alright",
        imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
        actors = "Robert Downey Jr., Chris Evans",
        imdbIdUrl = "https://imdb.com/title/tt4154796",
        title = "Avengers: Endgame"
    ),
    userProfileDTO: UserProfileDTO = UserProfileDTO(
        firstName = "First",
        lastName = "Last",
        email = "email@email.com",
        username = "username",
        amIFollowing = true,
        followers = 27,
        following = 5,
        birthday = "01-01-2000",
        followingUser = true,
        totalReviews = 7
    ),
    likedReview: MutableState<Boolean> = mutableStateOf(false),
    dislikedReview: MutableState<Boolean> = mutableStateOf(false),
    likeCount: MutableState<Int> =  mutableIntStateOf(0),
    dislikeCount: MutableState<Int> =  mutableIntStateOf(0)
) {

    val potentialComment = remember {
        mutableStateOf("")
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
            Row (horizontalArrangement = Arrangement.SpaceEvenly){
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = movieReviewDTO.imagePosterLink,
                    contentDescription = "${movieReviewDTO.title} ${stringResource(id = R.string.poster_image)}"
                )
                Column {
                    Text(text = movieReviewDTO.title)
                    Text(text = "Starring: ${movieReviewDTO.actors}")
                    Row (
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        for (i in 1..movieReviewDTO.rating){
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow, modifier = Modifier.weight(1f))
                        }

                        for (i in movieReviewDTO.rating..10){
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "Star", tint = Color.Gray, modifier = Modifier.weight(1f))
                        }
                    }

                }

            }
            Row (modifier = Modifier
                .fillMaxWidth()
                ){
                Column (modifier = Modifier.padding(8.dp) ){
                    Text(text = "${userProfileDTO.firstName} ${userProfileDTO.lastName}")
                }
            }

            Text(text = movieReviewDTO.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "- ${userProfileDTO.username}")
            Spacer(modifier = Modifier.height(8.dp))
            Card (){
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)){
                    Text(text = "Review's Comments")
                    Spacer(modifier = Modifier.height(8.dp))
                    Column{
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = potentialComment.value,
                            onValueChange = {potentialComment.value = it},
                            label = {
                                Text(text = "Add a comment")
                            },
                            trailingIcon = {
                                Icon(
                                    modifier = Modifier
                                        .clickable {/* TODO */ },
                                    imageVector = Icons.Filled.Send,
                                    contentDescription = "Leave Comment"
                                )
                            }
                        )
                    }
                }
            }

            Row (
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(modifier = Modifier
                    .clickable {

                        if(!likedReview.value) likeCount.value++ else likeCount.value--

                        likedReview.value = !likedReview.value

                        if (dislikedReview.value){
                            dislikedReview.value = false
                            dislikeCount.value--
                        }

                               },
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Thumbs up Icon",
                    tint = if (likedReview.value) accentColor else Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "${likeCount.value} Liked Review", style = MaterialTheme.typography.labelSmall)

                Spacer(modifier = Modifier.width(16.dp))

                Icon(modifier = Modifier
                    .clickable {
                        if(!dislikedReview.value) dislikeCount.value++ else dislikeCount.value--

                        dislikedReview.value = !dislikedReview.value

                        if (likedReview.value ){
                            likedReview.value = false
                            likeCount.value--
                        }

                    },
                    imageVector = Icons.Filled.ThumbDown,
                    contentDescription = "Thumbs down Icon",
                    tint = if (dislikedReview.value) accentColor else Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "${dislikeCount.value} Disliked Review", style = MaterialTheme.typography.labelSmall)
            }

        }



    }
}