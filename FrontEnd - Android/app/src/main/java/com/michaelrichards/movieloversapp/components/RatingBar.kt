package com.michaelrichards.movieloversapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    stars: Int = 10,
    starsColor: Color = Color.Yellow,
    onRatingChange: (Int) -> Unit = {}
) {

    Row (modifier = modifier){
        for (index in 1..stars){
            Icon(
                modifier = Modifier.clickable { onRatingChange(index) }.size(30.dp),
                imageVector = if (index <= rating) Icons.Rounded.Star else Icons.Outlined.StarOutline ,
                contentDescription = if (index <= rating) "Filled Star" else "Unfilled Star",
                tint = starsColor
            )
        }
    }

}