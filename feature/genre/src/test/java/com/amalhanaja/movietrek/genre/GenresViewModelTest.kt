package com.amalhanaja.movietrek.genre

import com.amalhanaja.movietrek.core.data.repository.DataRepository
import com.amalhanaja.movietrek.core.model.Genre
import com.amalhanaja.movietrek.core.tmdb.exception.TmdbException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Locale

class GenresViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var sut: GenresViewModel
    private val dataRepository = mockk<DataRepository>()

    @Before
    fun setUp() {
        sut = GenresViewModel(dataRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun genresUiState_whenInitialized_thenShowLoading() = runTest {
        // Act
        val result = sut.genresUiState.value

        // Assert
        assertEquals(GenresUiState.Loading, result)
    }

    @Test
    fun fetch_whenError_thenGenresUiStateError() = runTest(testDispatcher) {
        // Arrange
        coEvery { dataRepository.getMovieGenres(Locale.getDefault()) } returns flow {
            throw TmdbException("123", "Error", null)
        }

        // Act
        sut.fetch()

        // Assert
        assertTrue(sut.genresUiState.value is GenresUiState.Error)
    }

    @Test
    fun genresUiState_whenSuccess_thenShown() = runTest {
        // Arrange
        coEvery { dataRepository.getMovieGenres(any()) } returns flowOf(listOf(Genre(1, "Action")))

        // Act
        sut.fetch()

        // Assert
        assertTrue(sut.genresUiState.value is GenresUiState.Shown)
    }
}