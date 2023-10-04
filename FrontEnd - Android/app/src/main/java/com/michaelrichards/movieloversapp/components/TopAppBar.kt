package com.michaelrichards.movieloversapp.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import com.michaelrichards.movieloversapp.ui.theme.accentColor
import com.michaelrichards.movieloversapp.ui.theme.primaryFontColor
import com.michaelrichards.movieloversapp.ui.theme.secondaryColor
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    logoutViewModel: LogoutViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    LaunchedEffect(logoutViewModel, context) {
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

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
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
                    Spacer(modifier = Modifier.width(5.dp))
                }


                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(3.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(Screens.HomeScreen.route) {
                                    inclusive = true
                                }
                            }
                        },
                        text = stringResource(id = R.string.app_name),
                        color = accentColor,
                        fontStyle = FontStyle.Italic
                    )
                }

            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = secondaryColor)
    )
}