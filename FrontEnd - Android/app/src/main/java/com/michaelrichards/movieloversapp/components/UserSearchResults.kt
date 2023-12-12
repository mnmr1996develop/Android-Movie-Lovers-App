package com.michaelrichards.movieloversapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.ui.theme.accentColor


@Composable
fun UserSearchResults(
    modifier: Modifier = Modifier,
    userDataDTO: UserProfileDTO,
    following: MutableState<Boolean>,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "${userDataDTO.firstName} ${userDataDTO.lastName}",
                        style = MaterialTheme.typography.bodyLarge.copy(color = accentColor)
                    )
                    Text(
                        text = "@${userDataDTO.username}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(text = userDataDTO.email, style = MaterialTheme.typography.bodyMedium)
                }
                OutlinedButton(onClick = { onClick() }, modifier = Modifier.padding(1.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = if (!following.value) R.string.follow else R.string.unfollow))
                        Icon(
                            imageVector = if (!following.value) Icons.Filled.Add else Icons.Filled.Remove,
                            contentDescription = if (!following.value) "Follow Icon" else "Unfollow Icon"
                        )

                    }

                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            Divider(modifier = Modifier.fillMaxWidth())
        }


    }
}

private val demo = UserProfileDTO(
    "first",
    "last",
    "email",
    "username",
    "01/01/2000",
    89,
    198,
    true,
    false,
    89
)

private val following: MutableState<Boolean> = mutableStateOf(demo.amIFollowing)

@Preview
@Composable
private fun PreviewSearchResults(){

    UserSearchResults(userDataDTO = demo , following = following){

    }
}