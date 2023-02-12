package com.amalhanaja.movietrek.core.tmdb

import com.amalhanaja.movietrek.core.tmdb.exception.TmdbException
import com.amalhanaja.movietrek.core.tmdb.response.ErrorResponse
import com.amalhanaja.movietrek.core.tmdb.response.GenreResponse
import com.amalhanaja.movietrek.core.tmdb.response.MovieDetailResponse
import com.amalhanaja.movietrek.core.tmdb.response.MovieResponse
import com.amalhanaja.movietrek.core.tmdb.response.ReviewResponse
import com.amalhanaja.movietrek.core.tmdb.service.TmdbService
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Locale
import kotlin.coroutines.CoroutineContext

class TmdbClient(
    private val coroutineContext: CoroutineContext,
    private val apiKey: String,
) {

    private val gson by lazy { Gson() }

    private val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val url = chain.request().url.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()
            chain.proceed(
                request = chain.request().newBuilder()
                    .url(url)
                    .build()
            )
        }
        builder.build()
    }

    private val tmdbService: TmdbService by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrlProvider.provideBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }

    suspend fun getMovieGenres(locale: Locale): List<GenreResponse> {
        return executeCatching { tmdbService.getMovieGenres(language = locale.language).genres.orEmpty() }
    }

    suspend fun discoverMovie(locale: Locale, genreIds: List<Int>, page: Int): List<MovieResponse> {
        return executeCatching {
            tmdbService.discoverMovie(
                language = locale.language,
                withGenres = genreIds.joinToString(","),
                page = page
            ).results.orEmpty()
        }
    }

    suspend fun getMovieDetail(locale: Locale, id: Int): MovieDetailResponse {
        return executeCatching {
            tmdbService.getMovieDetail(
                id = id,
                language = locale.language
            )
        }
    }

    suspend fun getMovieReviews(locale: Locale, id: Int, page: Int): List<ReviewResponse> {
        return executeCatching {
            tmdbService.getMovieReviews(
                id = id,
                language = locale.language,
                page = page
            ).results.orEmpty()
        }
    }

    private suspend fun <T> executeCatching(executor: suspend () -> T) = withContext(coroutineContext) {
        try {
            executor()
        } catch (e: HttpException) {
            e.response()?.errorBody()?.string()?.let { errorBody ->
                val errorResponse: ErrorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                throw TmdbException(errorResponse.statusCode?.toString(), errorResponse.statusMessage, e)
            }
            throw TmdbException("UnknownError", null, e)
        }
    }

}