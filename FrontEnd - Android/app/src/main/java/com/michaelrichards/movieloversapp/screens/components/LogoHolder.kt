package com.michaelrichards.movieloversapp.screens.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.michaelrichards.movieloversapp.R

@Preview
@Composable
fun Logo(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.logo_transparent),
        contentDescription = stringResource(
            id = R.string.site_logo
        )
    )
}