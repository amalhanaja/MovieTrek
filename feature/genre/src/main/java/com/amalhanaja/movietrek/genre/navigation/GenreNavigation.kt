package com.amalhanaja.movietrek.genre.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.amalhanaja.movietrek.genre.DisplayableGenre
import com.amalhanaja.movietrek.genre.GenresRoute
import com.google.accompanist.navigation.animation.composable

const val genresNavigationRoute = "genres"

fun NavGraphBuilder.genresScreen(onGenreClick: (DisplayableGenre) -> Unit) {
    composable(route = genresNavigationRoute) {
        GenresRoute(
            genresViewModel = hiltViewModel(),
            onGenreClick = onGenreClick
        )
    }
}