package com.michaelrichards.movieloversapp.screens.CommentScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(
    modifier : Modifier = Modifier,
    viewModel: CommentViewModel = hiltViewModel(),
    movieReviewId: Long
) {

    LaunchedEffect(Unit) {
        viewModel.loadComments(movieReviewId)
    }

    val comments by viewModel.comments.collectAsState()

    var potentialComment by remember {
        mutableStateOf("")
    }



    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Comments", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) })
        }
    ){paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Bottom
        ){
            Column {
                if (comments.isEmpty()){
                    Box (modifier = Modifier.height(400.dp).fillMaxWidth(), contentAlignment = Alignment.Center){
                        Text(text = "Be the first to comment", style = MaterialTheme.typography.headlineLarge)
                    }
                }else{
                    LazyColumn {
                        items(items = comments){comment ->
                            Surface(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row {
                                    Column(modifier = Modifier.padding(5.dp)) {
                                        Text(text = "@${comment.commenter}", style = MaterialTheme.typography.bodySmall)
                                    }

                                    Text(text = comment.description, overflow = TextOverflow.Visible)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = potentialComment,
                onValueChange = {potentialComment = it},
                label = {
                    Text(text = "Add a comment")
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { viewModel.addComment(movieReviewId, potentialComment) },
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Leave Comment"
                    )
                }
            )
        }
    }

    Column(modifier = modifier
        .padding(16.dp)
        .fillMaxSize()) {



        Column {

        }
    }
}