package com.michaelrichards.movieloversapp.screens.SearchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.R
import com.michaelrichards.movieloversapp.components.BottomBar
import com.michaelrichards.movieloversapp.components.SearchResults
import com.michaelrichards.movieloversapp.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier= Modifier,
    navController: NavController
) {

    val searchString = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController, 1) }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding)
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OutlinedTextField(
                    modifier = Modifier.padding(10.dp),
                    value = searchString.value,
                    onValueChange = { searchString.value = it },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = stringResource(
                                id = R.string.search_for_movie
                            )
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.movie_name))},
                    singleLine = true
                )
            }
        }


    }

  /*  Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(10.dp),
                value = searchString.value,
                onValueChange = { searchString.value = it },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = stringResource(
                            id = R.string.search_for_movie
                        )
                    )
                },
                label = { Text(text = stringResource(id = R.string.movie_name))},
                singleLine = true
            )
        }
        Column {
            SearchResults()
        }
    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBody(
    modifier: Modifier = Modifier,
    searchString: MutableState<String>
) {

}