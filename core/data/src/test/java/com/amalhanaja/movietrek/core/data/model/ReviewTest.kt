package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.tmdb.response.AuthorDetailResponse
import com.amalhanaja.movietrek.core.tmdb.response.ReviewResponse
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class ReviewTest {

    @Test
    fun testReviewResponseToReview() {
        // Arrange
        val response = ReviewResponse(
            author = "Alfian",
            authorDetails = AuthorDetailResponse(
                rating = 9f,
                avatarPath = "/avatarPath",
            ),
            updatedAt = "2023-01-05T16:56:56.369Z",
            content = "Content",
        )

        // Act
        val result = response.toReview()

        // Assert
        assertEquals("Alfian", result.author)
        assertEquals(Instant.parse("2023-01-05T16:56:56.369Z").toLocalDateTime(TimeZone.currentSystemDefault()), result.timestamp)
        assertEquals(9f, result.rating)
        assertEquals("/avatarPath", result.avatarUrl)
        assertEquals("Content", result.content)
    }

    @Test
    fun testReviewResponseToReview_whenAvatarPathIsNull() {
        // Arrange
        val response = ReviewResponse(
            author = "Alfian",
            authorDetails = AuthorDetailResponse(
                rating = 9f,
            ),
            updatedAt = "2023-01-05T16:56:56.369Z",
            content = "Content",
        )

        // Act
        val result = response.toReview()

        // Assert
        assertEquals("Alfian", result.author)
        assertEquals(Instant.parse("2023-01-05T16:56:56.369Z").toLocalDateTime(TimeZone.currentSystemDefault()), result.timestamp)
        assertEquals(9f, result.rating)
        assertEquals("", result.avatarUrl)
        assertEquals("Content", result.content)
    }
}