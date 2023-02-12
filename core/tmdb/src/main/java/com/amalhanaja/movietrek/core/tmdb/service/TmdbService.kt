package com.amalhanaja.movietrek.core.tmdb.service

import com.amalhanaja.movietrek.core.tmdb.response.GenresResponse
import com.amalhanaja.movietrek.core.tmdb.response.MovieDetailResponse
import com.amalhanaja.movietrek.core.tmdb.response.MoviesResponse
import com.amalhanaja.movietrek.core.tmdb.response.ReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface TmdbService {

    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(
        @Query(value = "language") language: String,
    ): GenresResponse

    @GET("3/discover/movie")
    suspend fun discoverMovie(
        @Query(value = "language") language: String,
        @Query(value = "with_genres") withGenres: String,
        @Query(value = "page") page: Int,
    ): MoviesResponse

    @GET("3/movie/{id}?append_to_response=videos")
    suspend fun getMovieDetail(
        @Path(value = "id") id: Int,
        @Query(value = "language") language: String,
    ): MovieDetailResponse

    @GET("3/movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path(value = "id") id: Int,
        @Query(value = "language") language: String,
        @Query(value = "page") page: Int,
    ): ReviewsResponse
}