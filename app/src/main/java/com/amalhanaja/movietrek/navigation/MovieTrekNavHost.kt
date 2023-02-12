package com.amalhanaja.movietrek.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.amalhanaja.movietrek.genre.navigation.genresScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@Composable
fun MovieTrekNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        genresScreen(onGenreClick = {})
    }

}