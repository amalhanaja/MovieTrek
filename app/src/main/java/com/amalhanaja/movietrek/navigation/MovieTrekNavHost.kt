package com.amalhanaja.movietrek.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.amalhanaja.movietrek.detail.navigation.goToMovieDetail
import com.amalhanaja.movietrek.detail.navigation.movieDetailScreen
import com.amalhanaja.movietrek.discovermovie.navigation.discoverMovieScreen
import com.amalhanaja.movietrek.discovermovie.navigation.goToDiscoverMovie
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
        genresScreen(onGenreClick = { navController.goToDiscoverMovie(it.id, it.name) })
        discoverMovieScreen(
            onBack = { navController.popBackStack() },
            onMovieClick = { navController.goToMovieDetail(it.title, it.id) }
        )
        movieDetailScreen(onBack = { navController.popBackStack() })
    }

}