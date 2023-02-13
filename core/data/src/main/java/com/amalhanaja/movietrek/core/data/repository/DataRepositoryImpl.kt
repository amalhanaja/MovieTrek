package com.amalhanaja.movietrek.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amalhanaja.movietrek.core.data.model.toGenre
import com.amalhanaja.movietrek.core.data.model.toMovieDetail
import com.amalhanaja.movietrek.core.data.model.toMovieItem
import com.amalhanaja.movietrek.core.data.source.ReviewsPagingSource
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

    override fun getMovieReviews(locale: Locale, id: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(20),
        ) {
            ReviewsPagingSource(tmdbClient, locale, id)
        }.flow
    }

    override fun getMovieGenres(locale: Locale): Flow<List<Genre>> = flow {
        emit(tmdbClient.getMovieGenres(locale).map { it.toGenre() })
    }
}