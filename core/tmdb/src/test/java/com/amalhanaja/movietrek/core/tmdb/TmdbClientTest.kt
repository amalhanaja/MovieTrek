package com.amalhanaja.movietrek.core.tmdb

import com.amalhanaja.movietrek.core.tmdb.exception.TmdbException
import com.amalhanaja.movietrek.core.tmdb.response.GenreResponse
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Locale

class TmdbClientTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var tmdbClient: TmdbClient
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        tmdbClient = TmdbClient(testDispatcher, "SECRET")
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getMovieGenresWhenRespondError_shouldThrowTmdbException() = runTest(testDispatcher) {
        // Arrange
        useMockWebServer(401, readResource("error.json")) {

            // Act
            val exception = Assert.assertThrows("getMovieGenres unexpected exception", TmdbException::class.java) {
                runTest(testDispatcher) { tmdbClient.getMovieGenres(Locale("in")) }
            }

            // Assert
            assertEquals("7", exception.code)
            assertEquals("Message", exception.message)
        }
    }

    @Test
    fun getMovieGenresWhenRespondSuccess_shouldReturnExpectedGenreList() = runTest(testDispatcher) {
        // Arrange
        useMockWebServer(200, readResource("genre-list.json")) {

            // Act
            val result = tmdbClient.getMovieGenres(Locale("in"))

            // Assert
            val request = it.takeRequest()
            assertEquals("/3/genre/movie/list?language=in&api_key=SECRET", request.path)
            assertEquals(1, result.count())
            assertEquals(28, result.first().id)
            assertEquals("Action", result.first().name)
        }
    }

    @Test
    fun discoverMovieWhenRespondError_shouldThrowTmdbException() = runTest(testDispatcher) {
        useMockWebServer(401, readResource("error.json")) {

            // Act
            val exception = Assert.assertThrows("discoverMovie unexpected exception", TmdbException::class.java) {
                runTest(testDispatcher) { tmdbClient.discoverMovie(Locale("in"), emptyList(), 1) }
            }

            // Assert
            assertEquals("7", exception.code)
            assertEquals("Message", exception.message)
        }
    }

    @Test
    fun discoverMovieWhenRespondSuccess_shouldReturnExpectedResponse() = runTest(testDispatcher) {
        // Arrange
        useMockWebServer(200, readResource("discover-movie.json")) {

            // Act
            val result = tmdbClient.discoverMovie(Locale("in"), listOf(28, 12, 878), 1)

            // Assert
            val request = it.takeRequest()
            assertEquals("/3/discover/movie?language=in&with_genres=28%2C12%2C878&page=1&api_key=SECRET", request.path)
            assertEquals(1, result.count())
            assertEquals(false, result.first().adult)
            assertEquals("/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg", result.first().backdropPath)
            assertEquals(listOf(28, 12, 878), result.first().genreIds)
            assertEquals("en", result.first().originalLanguage)
            assertEquals("Black Panther: Wakanda Forever", result.first().originalTitle)
            assertEquals("Black Panther: Wakanda Forever Overview", result.first().overview)
            assertEquals(7141.639, result.first().popularity)
            assertEquals("/sv1xJUazXeYqALzczSZ3O6nkH75.jpg", result.first().posterPath)
            assertEquals("2022-11-09", result.first().releaseDate)
            assertEquals("Black Panther: Wakanda Forever", result.first().title)
            assertEquals(7.5f, result.first().voteAverage)
            assertEquals(2879L, result.first().voteCount)
            assertEquals(505642, result.first().id)
        }
    }

    @Test
    fun getMovieDetailWhenRespondError_shouldThrowTmdbException() = runTest(testDispatcher) {
        // Arrange
        useMockWebServer(401, readResource("error.json")) {

            // Act
            val exception = Assert.assertThrows("getMovieReviews unexpected exception", TmdbException::class.java) {
                runTest(testDispatcher) { tmdbClient.getMovieDetail(Locale("in"), 505642) }
            }

            // Assert
            assertEquals("7", exception.code)
            assertEquals("Message", exception.message)
        }
    }

    @Test
    fun getMovieDetailWhenRespondSuccess_shouldReturnExpectedResponse() = runTest(testDispatcher) {
        // Arrange
        useMockWebServer(200, readResource("movie-detail.json")) {

            // Act
            val result = tmdbClient.getMovieDetail(Locale("in"), 505642)

            // Assert
            val request = it.takeRequest()
            assertEquals("/3/movie/505642?append_to_response=videos&language=in&api_key=SECRET", request.path)
            assertEquals(false, result.adult)
            assertEquals("/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg", result.backdropPath)
            assertEquals(250000000L, result.budget)
            assertEquals(
                listOf(
                    GenreResponse(28, "Action"),
                    GenreResponse(12, "Adventure"),
                    GenreResponse(878, "Science Fiction")
                ),
                result.genres
            )
            assertEquals("https://wakandaforevertickets.com", result.homepage)
            assertEquals(505642, result.id)
            assertEquals("tt9114286", result.imdbId)
            assertEquals("en", result.originalLanguage)
            assertEquals("Black Panther: Wakanda Forever", result.originalTitle)
            assertEquals("Black Panther: Wakanda Forever Overview", result.overview)
            assertEquals(7141.639, result.popularity)
            assertEquals("/sv1xJUazXeYqALzczSZ3O6nkH75.jpg", result.posterPath)
            assertEquals(835000000L, result.revenue)
            assertEquals("2022-11-09", result.releaseDate)
            assertEquals(162, result.runtime)
            assertEquals("Released", result.status)
            assertEquals("Forever.", result.tagline)
            assertEquals("Black Panther: Wakanda Forever", result.title)
            assertEquals(7.492, result.voteAverage)
            assertEquals(2884, result.voteCount)
            assertEquals(1, result.videos?.results?.count())
            assertEquals("en", result.videos?.results?.first()?.iso6391)
            assertEquals("US", result.videos?.results?.first()?.iso31661)
            assertEquals("Official Teaser", result.videos?.results?.first()?.name)
            assertEquals("RlOB3UALvrQ", result.videos?.results?.first()?.key)
            assertEquals("YouTube", result.videos?.results?.first()?.site)
            assertEquals(1080, result.videos?.results?.first()?.size)
            assertEquals(true, result.videos?.results?.first()?.official)
            assertEquals("2022-07-24T01:22:23.000Z", result.videos?.results?.first()?.publishedAt)
            assertEquals("62dc9f30957e6d0056eff18d", result.videos?.results?.first()?.id)
        }
    }

    @Test
    fun getMovieReviewsWhenRespondError_shouldThrowTmdbException() = runTest(testDispatcher) {
        // Arrange
        useMockWebServer(401, readResource("error.json")) {

            // Act
            val exception = Assert.assertThrows("getMovieGenres unexpected exception", TmdbException::class.java) {
                runTest(testDispatcher) { tmdbClient.getMovieReviews(Locale("in"), 505642, 1) }
            }

            // Assert
            assertEquals("7", exception.code)
            assertEquals("Message", exception.message)
        }
    }

    @Test
    fun getMovieReviewsWhenRespondSuccess_shouldReturnExpectedGenreList() = runTest(testDispatcher) {
        // Arrange
        useMockWebServer(200, readResource("movie-reviews.json")) {

            // Act
            val result = tmdbClient.getMovieReviews(Locale("in"), 505642, 1)

            // Assert
            val request = it.takeRequest()
            assertEquals("/3/movie/505642/reviews?language=in&page=1&api_key=SECRET", request.path)
            assertEquals(1, result.count())
            assertEquals("r96sk", result.first().author)
            assertEquals("r96sk name", result.first().authorDetails?.name)
            assertEquals("r96sk", result.first().authorDetails?.username)
            assertEquals("/https://www.gravatar.com/avatar/96c2e0e4ac98450f9e8e3c0a0a40aad8.jpg", result.first().authorDetails?.avatarPath)
            assertEquals(8, result.first().authorDetails?.rating)
            assertEquals("Review content", result.first().content)
            assertEquals("2023-01-05T16:56:56.269Z", result.first().createdAt)
            assertEquals("63b7015843250f0082e8cd69", result.first().id)
            assertEquals("2023-01-05T16:56:56.369Z", result.first().updatedAt)
            assertEquals("https://www.themoviedb.org/review/63b7015843250f0082e8cd69", result.first().url)
        }
    }

    private suspend fun useMockWebServer(responseCode: Int, responseBody: String, fn: suspend (MockWebServer) -> Unit) {
        mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().setResponseCode(responseCode).setBody(responseBody))
        mockWebServer.start()
        mockkObject(BaseUrlProvider)
        every { BaseUrlProvider.provideBaseUrl() } returns mockWebServer.url("/").toString()
        fn(mockWebServer)
        mockWebServer.shutdown()
    }

    private fun readResource(fileName: String): String {
        val inputStream = requireNotNull(javaClass.classLoader?.getResourceAsStream(fileName))
        val source = inputStream.source().buffer()
        return source.readString(Charsets.UTF_8)
    }
}