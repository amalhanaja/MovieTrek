package com.amalhanaja.movietrek.core.data.repository

import com.amalhanaja.movietrek.core.data.model.toGenre
import com.amalhanaja.movietrek.core.data.model.toMovieDetail
import com.amalhanaja.movietrek.core.data.model.toMovieItem
import com.amalhanaja.movietrek.core.tmdb.TmdbClient
import com.amalhanaja.movietrek.core.tmdb.response.GenreResponse
import com.amalhanaja.movietrek.core.tmdb.response.MovieDetailResponse
import com.amalhanaja.movietrek.core.tmdb.response.MovieResponse
import com.amalhanaja.movietrek.core.tmdb.response.VideoResponse
import com.amalhanaja.movietrek.core.tmdb.response.VideosResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Locale

class DataRepositoryImplTest {

    private lateinit var sut: DataRepositoryImpl
    private val tmdbClient = mockk<TmdbClient>()

    @Before
    fun setUp() {
        sut = DataRepositoryImpl(tmdbClient)
    }

    @Test
    fun getMoviesGenres() = runTest {
        // Arrange
        val response = listOf(GenreResponse(1, "Action"))
        coEvery { tmdbClient.getMovieGenres(Locale("in")) } returns response

        // Act
        val result = sut.getMovieGenres(Locale("in")).first()

        // Assert
        assertEquals(response.map { it.toGenre() }, result)
    }

    @Test
    fun discoverMovie() = runTest {
        // Arrange
        val response = listOf(
            MovieResponse(
                posterPath = "/posterPath",
                id = 123,
                title = "Movie title"
            )
        )
        coEvery { tmdbClient.discoverMovie(Locale("in"), listOf(1, 2, 3), 1) } returns response

        // Act
        val result = sut.discoverMovie(Locale("in"), listOf(1, 2, 3), 1).first()

        // Assert
        assertEquals(response.map { it.toMovieItem() }, result)
    }

    @Test
    fun getMovieDetail() = runTest {
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
        coEvery { tmdbClient.getMovieDetail(Locale("in"), 1) } returns response

        // Act
        val result = sut.getMovieDetail(Locale("in"), 1).first()

        // Assert
        assertEquals(response.toMovieDetail(), result)
    }

//    @Test
//    fun getMovieReviews() = runTest {
//        // Arrange
//        val response = listOf(
//            ReviewResponse(
//                author = "Alfian",
//                authorDetails = AuthorDetailResponse(
//                    rating = 9,
//                ),
//                updatedAt = "2023-01-05T16:56:56.369Z",
//                content = "Content",
//            )
//        )
//        coEvery { tmdbClient.getMovieReviews(Locale("in"), 2, 1) } returns response
//
//        // Act
////        val result = sut.getMovieReviews(Locale("in"), 2, 1).first()
//
//        // Assert
////        assertEquals(response.map { it.toReview() }, result)
//    }
}