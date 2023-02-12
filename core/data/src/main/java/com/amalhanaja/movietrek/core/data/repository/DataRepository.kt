package com.amalhanaja.movietrek.core.data.repository

import com.amalhanaja.movietrek.core.model.Genre
import com.amalhanaja.movietrek.core.model.MovieDetail
import com.amalhanaja.movietrek.core.model.MovieItem
import com.amalhanaja.movietrek.core.model.Review
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface DataRepository {

    fun getMovieGenres(locale: Locale): Flow<List<Genre>>

    fun discoverMovie(locale: Locale, genreIds: List<Int>, page: Int): Flow<List<MovieItem>>

    fun getMovieDetail(locale: Locale, id: Int): Flow<MovieDetail>

    fun getMovieReviews(locale: Locale, id: Int, page: Int): Flow<List<Review>>
}