package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.tmdb.response.GenreResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreTest {

    @Test
    fun testGenreResponseToGenre() {
        // Arrange
        val response = GenreResponse(1, "name")

        // Act
        val result = response.toGenre()

        // Assert
        assertEquals(1, result.id)
        assertEquals("name", result.name)
    }
}