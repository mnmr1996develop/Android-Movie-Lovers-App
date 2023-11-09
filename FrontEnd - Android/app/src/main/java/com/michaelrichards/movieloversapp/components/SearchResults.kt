package com.michaelrichards.movieloversapp.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.dtos.SearchDataDTO
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.model.Description
import com.michaelrichards.movieloversapp.ui.theme.accentColor

@Composable
fun MovieSearchResults(
    modifier: Modifier = Modifier,
    description: Description
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Row (modifier = Modifier.padding(vertical = 8.dp)) {
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
                    Text(text = description.title, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                    Text(text = "Featuring: ${description.actors}")
                }

            }
            Divider(modifier = Modifier.fillMaxWidth())
        }


    }
}

@Composable
fun UserSearchResults(
    modifier: Modifier = Modifier,
    userDataDTO: SearchDataDTO,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Row (modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                Column (verticalArrangement = Arrangement.SpaceBetween){
                    Text(text = "${userDataDTO.firstName} ${userDataDTO.lastName}", style = MaterialTheme.typography.bodyLarge.copy(color = accentColor))
                    Text(text = "@${userDataDTO.username}", style = MaterialTheme.typography.bodySmall)
                    Text(text = userDataDTO.email, style = MaterialTheme.typography.bodyMedium)
                }
                OutlinedButton(onClick = { onClick()
                                            userDataDTO.following = !userDataDTO.following}, modifier = Modifier.padding(1.dp)) {
                    Row (horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
                        Text(text = if (!userDataDTO.following) "Follow" else "Unfollow" )
                        Icon(imageVector = if (!userDataDTO.following) Icons.Filled.Add else Icons.Filled.Remove, contentDescription = if (!userDataDTO.following) "Follow Icon" else "Unfollow Icon")

                    }

                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            Divider(modifier = Modifier.fillMaxWidth())
        }


    }
}


val testData  = UserDataDTO(
    firstName = "firstName",
    lastName = "lastName",
    email = "email@email.com",
    username = "username",
    birthday = "11-15-1998"
)

