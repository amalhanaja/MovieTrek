package com.amalhanaja.movietrek.genre

import com.amalhanaja.movietrek.core.model.Genre
import org.junit.Assert.assertEquals
import org.junit.Test

class DisplayableGenreTest {

    @Test
    fun genreToDisplayableGenre() {
        // Arrange
        val genre = Genre(1, "Action")

        // Act
        val result = genre.toDisplayableGenre()

        // Assert
        assertEquals(1, result.id)
        assertEquals("Action", result.name)
    }

}