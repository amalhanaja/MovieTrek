package com.amalhanaja.movietrek.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalhanaja.movietrek.core.data.repository.DataRepository
import com.amalhanaja.movietrek.core.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val _genresUiState: MutableStateFlow<GenresUiState> = MutableStateFlow(GenresUiState.Loading)
    val genresUiState: StateFlow<GenresUiState> = _genresUiState

    fun fetch() {
        dataRepository.getMovieGenres(Locale.getDefault())
            .map<List<Genre>, GenresUiState> { list -> GenresUiState.Shown(list.map { it.toDisplayableGenre() }) }
            .catch { emit(GenresUiState.Error(it.message.orEmpty())) }
            .onStart { emit(GenresUiState.Loading) }
            .onEach { _genresUiState.value = it }
            .launchIn(viewModelScope)
    }
}