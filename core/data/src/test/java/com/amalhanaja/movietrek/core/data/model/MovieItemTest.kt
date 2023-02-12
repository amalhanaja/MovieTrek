package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.tmdb.response.MovieResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieItemTest {

    @Test
    fun testMovieResponseToMovieItem() {
        // Arrange
        val response = MovieResponse(
            posterPath = "/posterPath",
            id = 123,
            title = "Movie title"
        )

        // Arrange
        val result = response.toMovieItem()

        // Assert
        assertEquals(123, result.id)
        assertEquals("Movie title", result.title)
        assertEquals("https://image.tmdb.org/t/p/w200/posterPath", result.posterUrl)
    }

    @Test
    fun testMovieResponseToMovieItem_whenPosterPathIsNull() {
        // Arrange
        val response = MovieResponse(
            posterPath = null,
            id = 123,
            title = "Movie title"
        )

        // Arrange
        val result = response.toMovieItem()

        // Assert
        assertEquals(123, result.id)
        assertEquals("Movie title", result.title)
        assertEquals("", result.posterUrl)
    }
}