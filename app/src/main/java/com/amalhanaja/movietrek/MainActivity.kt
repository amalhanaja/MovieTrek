package com.amalhanaja.movietrek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.amalhanaja.movietrek.core.designsystem.MovieTrekTheme
import com.amalhanaja.movietrek.genre.navigation.genresNavigationRoute
import com.amalhanaja.movietrek.navigation.MovieTrekNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            MovieTrekTheme {
                MovieTrekNavHost(
                    navController = navController,
                    startDestination = genresNavigationRoute,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}