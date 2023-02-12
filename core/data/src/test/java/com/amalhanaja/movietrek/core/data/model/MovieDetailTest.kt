package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.model.Genre
import com.amalhanaja.movietrek.core.model.Video
import com.amalhanaja.movietrek.core.tmdb.response.GenreResponse
import com.amalhanaja.movietrek.core.tmdb.response.MovieDetailResponse
import com.amalhanaja.movietrek.core.tmdb.response.VideoResponse
import com.amalhanaja.movietrek.core.tmdb.response.VideosResponse
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.DurationUnit

class MovieDetailTest {

    @Test
    fun testMovieDetailResponseToMovieDetail() {
        // Arrange
        val response = MovieDetailResponse(
            id = 1,
            backdropPath = "/backdropPath",
            posterPath = "/posterPath",
            title = "Title",
            releaseDate = "2022-11-09",
            genres = listOf(GenreResponse(1, "name")),
            runtime = 162,
            voteAverage = 7.5f,
            overview = "Overview",
            videos = VideosResponse(
                results = listOf(VideoResponse(key = "key", name = "Trailer"))
            )
        )

        // Act
        val result = response.toMovieDetail()

        // Assert
        assertEquals(1, result.id)
        assertEquals("https://image.tmdb.org/t/p/w500/backdropPath", result.backdropUrl)
        assertEquals("https://image.tmdb.org/t/p/w200/posterPath", result.posterUrl)
        assertEquals("Title", result.title)
        assertEquals(LocalDate(2022, 11, 9), result.releaseDate)
        assertEquals(listOf(Genre(1, "name")), result.genres)
        assertEquals(162, result.duration.toInt(DurationUnit.MINUTES))
        assertEquals(7.5f, result.rating)
        assertEquals("Overview", result.overview)
        assertEquals(
            listOf(
                Video(
                    videoUrl = "https://www.youtube.com/watch?v=key",
                    thumbnailUrl = "https://img.youtube.com/vi/key/0.jpg",
                    title = "Trailer",
                )
            ),
            result.videos
        )
    }

    @Test
    fun testMovieDetailResponseToMovieDetail_whenBackdropPathAndPosterPathIsNull() {
        // Arrange
        val response = MovieDetailResponse(
            id = 1,
            backdropPath = null,
            posterPath = null,
            title = "Title",
            releaseDate = "2022-11-09",
            genres = listOf(GenreResponse(1, "name")),
            runtime = 162,
            voteAverage = 7.5f,
            overview = "Overview",
            videos = VideosResponse(
                results = listOf(VideoResponse(key = "key", name = "Trailer"))
            )
        )

        // Act
        val result = response.toMovieDetail()

        // Assert
        assertEquals(1, result.id)
        assertEquals("", result.backdropUrl)
        assertEquals("", result.posterUrl)
        assertEquals("Title", result.title)
        assertEquals(LocalDate(2022, 11, 9), result.releaseDate)
        assertEquals(listOf(Genre(1, "name")), result.genres)
        assertEquals(162, result.duration.toInt(DurationUnit.MINUTES))
        assertEquals(7.5f, result.rating)
        assertEquals("Overview", result.overview)
        assertEquals(
            listOf(
                Video(
                    videoUrl = "https://www.youtube.com/watch?v=key",
                    thumbnailUrl = "https://img.youtube.com/vi/key/0.jpg",
                    title = "Trailer",
                )
            ),
            result.videos
        )
    }
}