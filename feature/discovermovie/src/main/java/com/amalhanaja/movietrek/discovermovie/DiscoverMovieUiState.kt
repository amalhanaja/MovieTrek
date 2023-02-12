package com.amalhanaja.movietrek.discovermovie

sealed interface DiscoverMovieUiState {
    object Loading : DiscoverMovieUiState
    object LoadingMoreItems : DiscoverMovieUiState
    data class ErrorLoadMoreItem(val message: String) : DiscoverMovieUiState
    data class ErrorLoad(val message: String) : DiscoverMovieUiState
    object Loaded : DiscoverMovieUiState
}