package com.amalhanaja.movietrek.discovermovie.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.amalhanaja.movietrek.core.model.MovieItem
import com.amalhanaja.movietrek.discovermovie.DiscoverMovieRoute
import com.google.accompanist.navigation.animation.composable

fun NavController.goToDiscoverMovie(
    genreId: Int,
    genre: String,
) {
    navigate("genres/$genreId?name=$genre")
}

fun NavGraphBuilder.discoverMovieScreen(onBack: () -> Unit, onMovieClick: (MovieItem) -> Unit) {
    composable(
        route = "genres/{id}?name={name}",
        arguments = listOf(
            navArgument("id") { type = NavType.IntType },
            navArgument("name") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        DiscoverMovieRoute(
            discoverMovieViewModel = hiltViewModel(),
            genre = requireNotNull(backStackEntry.arguments?.getString("name")),
            genreId = requireNotNull(backStackEntry.arguments?.getInt("id")),
            onBack = onBack,
            onMovieClick = onMovieClick,
        )
    }
}