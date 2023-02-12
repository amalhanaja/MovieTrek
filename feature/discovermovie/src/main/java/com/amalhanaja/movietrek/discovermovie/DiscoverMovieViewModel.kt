package com.amalhanaja.movietrek.discovermovie

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalhanaja.movietrek.core.data.repository.DataRepository
import com.amalhanaja.movietrek.core.model.MovieItem
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
class DiscoverMovieViewModel @Inject constructor(
    private val dataRepository: DataRepository,
) : ViewModel() {

    private companion object {
        const val FIRST_PAGE = 1
        const val PAGE_INCREMENT = 1
    }

    private val params = MutableStateFlow<Params?>(null)
    private val _discoverMovieUiState = MutableStateFlow<DiscoverMovieUiState>(DiscoverMovieUiState.Loading)
    val movieItems = mutableStateListOf<MovieItem>()

    val discoverMovieUiState: StateFlow<DiscoverMovieUiState> = _discoverMovieUiState

    fun load(locale: Locale, genreIds: List<Int>) {
        if (movieItems.isEmpty()) refresh(locale, genreIds)
        else nextPage()
    }

    private fun refresh(locale: Locale, genreIds: List<Int>) {
        movieItems.clear()
        params.updateAndGet {
            Params(locale, genreIds, FIRST_PAGE)
        }?.let(::fetch)
    }

    fun retry() {
        params.value?.let(::fetch)
    }

    private fun nextPage() {
        params.updateAndGet {
            val currentPage = it?.page ?: FIRST_PAGE
            it?.copy(page = currentPage + PAGE_INCREMENT)
        }?.let(::fetch)
    }

    private fun fetch(params: Params) {
        dataRepository.discoverMovie(locale = params.locale, genreIds = params.genreIds, page = params.page)
            .map<List<MovieItem>, DiscoverMovieUiState> {
                movieItems.addAll(it)
                DiscoverMovieUiState.Loaded
            }
            .catch {
                val message = it.message.orEmpty()
                when (params.page) {
                    FIRST_PAGE -> emit(DiscoverMovieUiState.ErrorLoad(message))
                    else -> emit(DiscoverMovieUiState.ErrorLoadMoreItem(message))
                }
            }
            .onStart {
                when (params.page) {
                    FIRST_PAGE -> emit(DiscoverMovieUiState.Loading)
                    else -> emit(DiscoverMovieUiState.LoadingMoreItems)
                }
            }
            .onEach { _discoverMovieUiState.value = it }
            .launchIn(viewModelScope)
    }

    private data class Params(val locale: Locale, val genreIds: List<Int>, val page: Int)
}