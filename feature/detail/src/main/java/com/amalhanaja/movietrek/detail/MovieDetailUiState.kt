package com.amalhanaja.movietrek.detail

import com.amalhanaja.movietrek.core.model.MovieDetail

sealed interface MovieDetailUiState {
    object Loading : MovieDetailUiState
    data class Shown(val detail: MovieDetail) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}