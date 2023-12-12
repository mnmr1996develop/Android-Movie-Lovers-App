package com.michaelrichards.movieloversapp.screens.AccountScreen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.LogoutButton
import com.michaelrichards.movieloversapp.components.TopBar
import com.michaelrichards.movieloversapp.navigation.Graphs

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController, 3) }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding)
        ) {
            LogoutButton {
                viewModel.logout()
                Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(Graphs.AuthGraph.routeName) {
                    popUpTo(
                        Graphs.MainGraph.routeName
                    ) {
                        inclusive = true
                    }
                }
            }/*Button(
                onClick = {
                    viewModel.logout()
                    Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(Graphs.AuthGraph.routeName) {
                        popUpTo(
                            Graphs.MainGraph.routeName
                        ) {
                            inclusive = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = stringResource(id = R.string.logout), modifier = Modifier.padding(10.dp))
            }*/
        }


    }
}