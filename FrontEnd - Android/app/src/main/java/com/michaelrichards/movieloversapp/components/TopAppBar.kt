package com.michaelrichards.movieloversapp.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.repositories.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String = "Michael Richards",
    navController: NavController,
    showSearchBar: Boolean = true,
    logoutViewModel: LogoutViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    LaunchedEffect(logoutViewModel, context){
        logoutViewModel.authResults.collect { res ->
            when (res) {
                is AuthResult.UnAuthorized -> {
                    Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
                    delay(Toast.LENGTH_LONG.toLong())
                    navController.navigate(Graphs.AuthGraph.routeName) {
                        popUpTo(Graphs.MainGraph.routeName) {
                            inclusive = true
                        }
                    }
                }
                else -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    TopAppBar(title = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Logo(modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(Screens.HomeScreen.route) {
                                inclusive = true
                            }
                        }
                    })
            }
            Column(
                modifier = Modifier.weight(3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily.Cursive)
                )
            }

           
            if (showSearchBar) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable { navController.navigate(Screens.SearchScreen.route)},
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Go to search page",
                    )
                }

            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                                   logoutViewModel.logout()
                        },
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Go to search page",
                )
            }
        }
    }
    )
}