package com.michaelrichards.movieloversapp.screens.LoginScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.navigation.Screens
import com.michaelrichards.movieloversapp.components.AuthInputFields
import com.michaelrichards.movieloversapp.components.Logo
import com.michaelrichards.movieloversapp.data.State
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import com.michaelrichards.movieloversapp.ui.theme.backgroundColor
import kotlinx.coroutines.delay


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val state = loginViewModel.state
    val context = LocalContext.current
    LaunchedEffect(loginViewModel, context) {
        loginViewModel.authResults.collect { res ->
            when (res) {
                is AuthResult.Authorized -> {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    delay(Toast.LENGTH_LONG.toLong())
                    navController.navigate(Graphs.MainGraph.routeName) {
                        popUpTo(Graphs.AuthGraph.routeName) {
                            inclusive = true
                        }
                    }
                }

                is AuthResult.UnknownError -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(context, "Bad Credentials", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


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

    if (state.loading) {
        BigCircularIndicator()
    } else {
        loginMainBody(
            modifier,
            username,
            password,
            passwordVisible,
            loginError,
            state,
            loginViewModel,
            navController
        )
    }
}

@Composable
private fun loginMainBody(
    modifier: Modifier,
    username: MutableState<String>,
    password: MutableState<String>,
    passwordVisible: MutableState<Boolean>,
    loginError: MutableState<Boolean>,
    state: State,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Logo(
                modifier = Modifier
                    .weight(5f)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .weight(11f),
                verticalArrangement = Arrangement.Center,
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
                Spacer(modifier = Modifier.height(8.dp))
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
                    isError = loginError.value,
                    keyboardActions = KeyboardActions(onGo = {
                        if (!state.loading) {
                            login(
                                loginViewModel,
                                username,
                                password
                            )
                        }
                    })
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        login(loginViewModel, username, password)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.headlineSmall.copy(fontFamily = FontFamily.Cursive)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Bottom
            ) {

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(Screens.RegistrationScreen.route) }
                ) {
                    Text(text = stringResource(id = R.string.sign_up))
                }
            }
        }
    }
}

private fun login(
    loginViewModel: LoginViewModel,
    username: MutableState<String>,
    password: MutableState<String>
) {
    loginViewModel
        .loginWithUsernameAndPassword(
            username = username.value,
            password = password.value
        )
}

@Preview
@Composable
fun previewLoginScreen() {
    LoginScreen(
        navController = NavController(LocalContext.current)
    )
}

@Composable
fun BigCircularIndicator() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}
