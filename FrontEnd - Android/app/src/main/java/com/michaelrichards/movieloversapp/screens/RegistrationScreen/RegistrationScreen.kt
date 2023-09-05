package com.michaelrichards.movieloversapp.screens.RegistrationScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.components.AuthInputFields
import com.michaelrichards.movieloversapp.components.Logo
import com.michaelrichards.movieloversapp.ui.theme.backgroundColor
import java.time.LocalDate

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    
    val firstName = remember {
        mutableStateOf("")
    }
    
    val lastName = remember {
        mutableStateOf("")
    }
    
    val username = remember {
        mutableStateOf("")
    }
    
    val password = remember {
        mutableStateOf("")
    }

    val retypedPassword = remember {
        mutableStateOf("")
    }
    
    val email = remember {
        mutableStateOf("")
    }
    
    val birthday = remember {
        mutableStateOf(LocalDate.now())
    }

    val passwordVisible1 = remember {
        mutableStateOf(false)
    }

    val passwordVisible2 = remember {
        mutableStateOf(false)
    }
    
    Surface(
        modifier = modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Logo(modifier = Modifier.size(200.dp))
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AuthInputFields(
                    valueState = firstName,
                    label = stringResource(id = R.string.first_name),
                    enabled = true,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = stringResource(id = R.string.first_name),
                            tint = Color.White
                        )
                    },
                )

                AuthInputFields(
                    valueState = lastName,
                    label = stringResource(id = R.string.last_name),
                    enabled = true,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = stringResource(id = R.string.last_name),
                            tint = Color.White
                        )
                    },
                )

                AuthInputFields(
                    valueState = username,
                    label = stringResource(id = R.string.username),
                    enabled = true,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = stringResource(id = R.string.username),
                            tint = Color.White
                        )
                    },
                )

                AuthInputFields(
                    valueState = email,
                    label = stringResource(id = R.string.email),
                    enabled = true,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.email_icon),
                            contentDescription = stringResource(id = R.string.email),
                            tint = Color.White
                        )
                    },
                )

                AuthInputFields(
                    valueState = password,
                    label = stringResource(id = R.string.password),
                    visualTransformation = if (passwordVisible1.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    imeAction = ImeAction.Next,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (passwordVisible1.value) R.drawable.hide_eye else R.drawable.show_eye),
                            contentDescription = stringResource(id = if (passwordVisible1.value) R.string.hide_password else R.string.show_password),
                            modifier = Modifier.clickable {
                                passwordVisible1.value = !passwordVisible1.value
                            },
                            tint = Color.White
                        )
                    }
                )

                AuthInputFields(
                    valueState = retypedPassword,
                    label = stringResource(id = R.string.re_password),
                    visualTransformation = if (passwordVisible2.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    imeAction = ImeAction.Go,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = if (passwordVisible2.value) R.drawable.hide_eye else R.drawable.show_eye),
                            contentDescription = stringResource(id = if (passwordVisible2.value) R.string.hide_password else R.string.show_password),
                            modifier = Modifier.clickable {
                                passwordVisible2.value = !passwordVisible2.value
                            },
                            tint = Color.White
                        )
                    }
                )

                Button(onClick = { viewModel.register(firstName = firstName.value, lastName = lastName.value, username = username.value, email = email.value, password = password.value, birthday = birthday.value , navController = navController) }) {
                    Text(text = stringResource(id = R.string.sign_up))
                }
            }
            
        }
    }
}


@Preview
@Composable
fun PreviewReg() {
    RegistrationScreen(navController = NavController(LocalContext.current))
}
