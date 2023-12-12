package com.michaelrichards.movieloversapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.model.Description
import com.michaelrichards.movieloversapp.ui.theme.accentColor

@Composable
fun MovieSearchResults(
    modifier: Modifier = Modifier,
    description: Description,
    onClick: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                Column {
                    AsyncImage(
                        modifier = Modifier.size(200.dp),
                        model = description.imagePosterLink,
                        contentDescription = "${description.title} ${stringResource(id = R.string.poster_image)}"
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = description.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(text = "Featuring: ${description.actors}")
                }

            }
            Divider(modifier = Modifier.fillMaxWidth())
        }


    }
}



