package com.michaelrichards.movieloversapp.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.dtos.MovieReviewDataDTO
import com.michaelrichards.movieloversapp.screens.CommentScreen.CommentScreen
import com.michaelrichards.movieloversapp.ui.theme.accentColor

private const val TAG = "ReviewColumn"

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ReviewColumn(
    reviewMovieDTO: MovieReviewDataDTO,
    onUpVoteIconClick: () -> Unit,
    onDownVoteIconClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSendIconClick: () -> Unit = {}
) {

    val potentialComment = remember {
        mutableStateOf("")
    }


    val likedReview: MutableState<Boolean> = remember {
        mutableStateOf(reviewMovieDTO.userInUpVote)
    }
    val dislikedReview: MutableState<Boolean> = remember {
        mutableStateOf(reviewMovieDTO.userInDownVote)
    }
    val likeCount: MutableState<Int> = remember {
        mutableIntStateOf(reviewMovieDTO.upVotes)
    }
    val dislikeCount = remember {
        mutableIntStateOf(reviewMovieDTO.downVotes)
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            if (reviewMovieDTO.isUserTheReviewer) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Post",
                        modifier = Modifier.clickable { onEditClick() })
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.clickable { onDeleteClick() })
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                AsyncImage(
                    modifier = Modifier.size(80.dp),
                    model = reviewMovieDTO.imagePosterLink,
                    contentDescription = "${reviewMovieDTO.title} ${stringResource(id = R.string.poster_image)}"
                )
                Column {
                    Text(text = reviewMovieDTO.title)
                    Text(text = "Starring: ${reviewMovieDTO.actors}")
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        RatingBar(rating = reviewMovieDTO.rating)

                    }

                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "${reviewMovieDTO.reviewerFirstName} ${reviewMovieDTO.reviewerLastName}  - @${reviewMovieDTO.reviewerUsername}")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))


            val minSeeMoreLength = 200

            var seeMore by remember {
                mutableStateOf(reviewMovieDTO.description.length >= minSeeMoreLength)
            }



            Log.d(TAG, "ReviewColumn: $seeMore")

            when (seeMore) {
                true -> {
                    Column {
                        Text(text = "${reviewMovieDTO.description.substring(0..minSeeMoreLength)}…")
                        Text(
                            text = stringResource(id = R.string.see_more),
                            modifier = Modifier.clickable { seeMore = false })
                    }


                }

                false -> {
                    Column {
                        Text(text = reviewMovieDTO.description)
                        if (reviewMovieDTO.description.length >= minSeeMoreLength){
                            Text(
                                text = stringResource(id = R.string.see_less),
                                modifier = Modifier.clickable { seeMore = true })
                        }
                    }
                }
            }



            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onUpVoteIconClick()
                            if (!likedReview.value) likeCount.value++ else likeCount.value--

                            likedReview.value = !likedReview.value

                            if (dislikedReview.value) {
                                dislikedReview.value = false
                                dislikeCount.intValue--
                            }

                        },
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Thumbs up Icon",
                    tint = if (likedReview.value) accentColor else Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${likeCount.value} Liked Review",
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    modifier = Modifier
                        .clickable {
                            if (!dislikedReview.value) dislikeCount.intValue++ else dislikeCount.intValue--

                            dislikedReview.value = !dislikedReview.value
                            onDownVoteIconClick()
                            if (likedReview.value) {
                                likedReview.value = false
                                likeCount.value--
                            }

                        },
                    imageVector = Icons.Filled.ThumbDown,
                    contentDescription = "Thumbs down Icon",
                    tint = if (dislikedReview.value) accentColor else Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${dislikeCount.intValue} Disliked Review",
                    style = MaterialTheme.typography.labelSmall
                )


            }

            val sheetState = rememberModalBottomSheetState()
            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }
            OutlinedButton(onClick = { isSheetOpen = true }) {
                Text(text = "View Comments")
            }

            if (isSheetOpen) {
                ModalBottomSheet(
                    onDismissRequest = { isSheetOpen = false },
                    sheetState = sheetState
                ) {
                    CommentScreen(movieReviewId = reviewMovieDTO.reviewId)
                }
            }


        }

    }
}