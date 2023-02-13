package com.amalhanaja.movietrek.detail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.amalhanaja.movietrek.detail.MovieDetailRoute
import com.google.accompanist.navigation.animation.composable

fun NavController.goToMovieDetail(title: String, id: Int) {
    navigate("movie/$id?title=$title")
}

fun NavGraphBuilder.movieDetailScreen(
    onBack: () -> Unit,
) {
    composable(
        route = "movie/{id}?title={title}",
        arguments = listOf(
            navArgument("id") { type = NavType.IntType },
            navArgument("title") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        MovieDetailRoute(
            title = requireNotNull(backStackEntry.arguments?.getString("title")),
            id = requireNotNull(backStackEntry.arguments?.getInt("id")),
            movieDetailViewModel = hiltViewModel(),
            onBack = onBack
        )
    }
}