package com.michaelrichards.movieloversapp.screens.SplashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.michaelrichards.movieloversapp.components.Logo
import com.michaelrichards.movieloversapp.navigation.Graphs
import com.michaelrichards.movieloversapp.repositories.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
     val scale = remember {
         Animatable(0f)
     }
    val context = LocalContext.current
    LaunchedEffect(viewModel, context){
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = { OvershootInterpolator(8f).getInterpolation(it) })
        )
        viewModel.authResults.collect{res ->
            when(res){
                is AuthResult.Authorized -> {
                    navController.navigate(Graphs.MainGraph.routeName){
                        popUpTo(Graphs.StartGraph.routeName){
                            inclusive = true
                        }
                    }
                }
                else -> {
                    navController.navigate(Graphs.AuthGraph.routeName){
                        popUpTo(Graphs.StartGraph.routeName){
                            inclusive = true
                        }
                    }
                }
            }
        }

    }



    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background)
        .wrapContentSize(Alignment.Center))
    {
        Box(
            modifier = Modifier
                .size(400.dp)
                .scale(scale.value)
                .clip(CircleShape)
                .background(Color(0xFFdc8d2d))
        ){
            Logo()
        }
    }

}