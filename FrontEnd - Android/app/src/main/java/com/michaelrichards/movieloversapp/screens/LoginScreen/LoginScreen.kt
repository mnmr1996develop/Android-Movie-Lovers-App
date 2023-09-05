package com.michaelrichards.movieloversapp.screens.LoginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.screens.components.AuthInputFields
import com.michaelrichards.movieloversapp.screens.components.Logo
import com.michaelrichards.movieloversapp.ui.theme.backgroundColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {

    val username = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    val loginError = remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Logo(
                modifier = Modifier.size(250.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 30.dp, start = 5.dp, end = 5.dp, bottom = 5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AuthInputFields(
                    valueState = username,
                    label = "Username",
                    enabled = true,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "Username Field",
                            tint = Color.White
                        )
                    },
                )

                AuthInputFields(
                    valueState = password,
                    label = "Password",
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    imeAction = ImeAction.Go,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (passwordVisible.value) R.drawable.hide_eye else R.drawable.show_eye),
                            contentDescription = if (passwordVisible.value) "Hide Password" else "Show Password",
                            modifier = Modifier.clickable {
                                passwordVisible.value = !passwordVisible.value
                            },
                            tint = Color.White
                        )
                    },
                    isError = loginError.value
                )

                Button(
                    onClick = {
                        loginViewModel
                            .loginWithUsernameAndPassword(
                                username = username.value,
                                password = password.value,
                                navController = navController
                            )
                    }
                ) {
                    Text(text = stringResource(id = R.string.login))
                }

                Text(text = "or")

                Button(onClick = { navController.navigate(Screens.RegistrationScreen.route) }) {
                    Text(text = stringResource(id = R.string.sign_up))
                }
            }
        }
    }

}

@Preview
@Composable
fun previewLoginScreen() {
    LoginScreen(
        navController = NavController(LocalContext.current)
    )
}

