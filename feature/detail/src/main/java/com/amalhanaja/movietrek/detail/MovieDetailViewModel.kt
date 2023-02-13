package com.amalhanaja.movietrek.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalhanaja.movietrek.core.data.repository.DataRepository
import com.amalhanaja.movietrek.core.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.updateAndGet
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val dataRepository: DataRepository,
) : ViewModel() {


    private val params: MutableStateFlow<Params?> = MutableStateFlow(null)
    private val _movieDetailUiState: MutableStateFlow<MovieDetailUiState> = MutableStateFlow(MovieDetailUiState.Loading)
    val movieDetailUiState: StateFlow<MovieDetailUiState> = _movieDetailUiState

    fun fetch(locale: Locale, id: Int) {
        params.updateAndGet { Params(locale, id) }?.let(::getMovieDetail)
    }

    private fun getMovieDetail(params: Params) {
        dataRepository.getMovieDetail(params.locale, params.id)
            .map<MovieDetail, MovieDetailUiState> { MovieDetailUiState.Shown(it) }
            .catch { emit(MovieDetailUiState.Error(it.message.orEmpty())) }
            .onStart { emit(MovieDetailUiState.Loading) }
            .onEach { _movieDetailUiState.value = it }
            .launchIn(viewModelScope)
    }

    private data class Params(val locale: Locale, val id: Int)
}