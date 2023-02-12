package com.amalhanaja.movietrek.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalhanaja.movietrek.core.data.repository.DataRepository
import com.amalhanaja.movietrek.core.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    dataRepository: DataRepository,
) : ViewModel() {

    val genresUiState: StateFlow<GenresUiState> = dataRepository.getMovieGenres(Locale.getDefault())
        .map<List<Genre>, GenresUiState> { list -> GenresUiState.Shown(list.map { it.toDisplayableGenre() }) }
        .catch { emit(GenresUiState.Error(it.message.orEmpty())) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = GenresUiState.Loading
        )

}