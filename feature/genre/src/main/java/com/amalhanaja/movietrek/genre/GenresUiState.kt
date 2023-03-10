package com.amalhanaja.movietrek.genre

sealed interface GenresUiState {
    object Loading : GenresUiState
    data class Shown(val genres: List<DisplayableGenre>) : GenresUiState

    data class Error(val title: String) : GenresUiState
}