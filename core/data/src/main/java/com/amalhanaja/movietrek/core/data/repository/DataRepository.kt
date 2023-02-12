package com.amalhanaja.movietrek.core.data.repository

import com.amalhanaja.movietrek.core.model.Genre
import com.amalhanaja.movietrek.core.model.MovieDetail
import com.amalhanaja.movietrek.core.model.MovieItem
import com.amalhanaja.movietrek.core.model.Review
import java.util.Locale

interface DataRepository {

    suspend fun getMovieGenres(locale: Locale): List<Genre>

    suspend fun discoverMovie(locale: Locale, genreIds: List<Int>, page: Int): List<MovieItem>

    suspend fun getMovieDetail(locale: Locale, id: Int): MovieDetail

    suspend fun getMovieReviews(locale: Locale, id: Int, page: Int): List<Review>
}