package com.michaelrichards.movieloversapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.michaelrichards.movieloversapp.R

@Preview
@Composable
fun Logo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = R.drawable.logo_transparent),
            contentDescription = stringResource(
                id = R.string.site_logo
            ),
            contentScale = ContentScale.Fit
        )
    }
}