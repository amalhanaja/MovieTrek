package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.tmdb.response.VideoResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoTest {

    @Test
    fun testVideoResponseToVideo() {
        // Arrange
        val response = VideoResponse(key = "key", name = "Trailer")

        // Act
        val result = response.toVideo()

        // Assert
        assertEquals("https://www.youtube.com/watch?v=key", result.videoUrl)
        assertEquals("https://img.youtube.com/vi/key/0.jpg", result.thumbnailUrl)
        assertEquals("Trailer", result.title)
        assertEquals("key", result.videoId)
    }
}