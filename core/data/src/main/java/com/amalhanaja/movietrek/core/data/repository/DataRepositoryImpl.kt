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
import java.util.Locale

class DataRepositoryImpl constructor(
    private val tmdbClient: TmdbClient,
) : DataRepository {

    override suspend fun discoverMovie(locale: Locale, genreIds: List<Int>, page: Int): List<MovieItem> {
        return tmdbClient.discoverMovie(locale, genreIds, page)
            .map { it.toMovieItem() }
    }

    override suspend fun getMovieDetail(locale: Locale, id: Int): MovieDetail {
        return tmdbClient.getMovieDetail(locale, id).toMovieDetail()
    }

    override suspend fun getMovieReviews(locale: Locale, id: Int, page: Int): List<Review> {
        return tmdbClient.getMovieReviews(locale, id, page).map { it.toReview() }
    }

    override suspend fun getMovieGenres(locale: Locale): List<Genre> {
        return tmdbClient.getMovieGenres(locale).map { it.toGenre() }
    }
}