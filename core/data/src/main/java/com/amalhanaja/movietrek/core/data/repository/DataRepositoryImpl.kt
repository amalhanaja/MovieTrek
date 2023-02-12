package com.amalhanaja.movietrek.core.data.repository

import com.amalhanaja.movietrek.core.data.model.toGenre
import com.amalhanaja.movietrek.core.data.model.toMovieDetail
import com.amalhanaja.movietrek.core.data.model.toMovieItem
import com.amalhanaja.movietrek.core.data.model.toReview
import com.amalhanaja.movietrek.core.model.Genre
import com.amalhanaja.movietrek.core.model.MovieDetail
import com.amalhanaja.movietrek.core.model.MovieItem
import com.amalhanaja.movietrek.core.model.Review
import com.amalhanaja.movietrek.core.tmdb.TmdbClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale

class DataRepositoryImpl constructor(
    private val tmdbClient: TmdbClient,
) : DataRepository {

    override fun discoverMovie(locale: Locale, genreIds: List<Int>, page: Int): Flow<List<MovieItem>> = flow {
        emit(tmdbClient.discoverMovie(locale, genreIds, page).map { it.toMovieItem() })
    }

    override fun getMovieDetail(locale: Locale, id: Int): Flow<MovieDetail> = flow {
        emit(tmdbClient.getMovieDetail(locale, id).toMovieDetail())
    }

    override fun getMovieReviews(locale: Locale, id: Int, page: Int): Flow<List<Review>> = flow {
        emit(tmdbClient.getMovieReviews(locale, id, page).map { it.toReview() })
    }

    override fun getMovieGenres(locale: Locale): Flow<List<Genre>> = flow {
        emit(tmdbClient.getMovieGenres(locale).map { it.toGenre() })
    }
}