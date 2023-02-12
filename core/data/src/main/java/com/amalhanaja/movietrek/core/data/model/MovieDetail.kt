package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.model.MovieDetail
import com.amalhanaja.movietrek.core.tmdb.response.MovieDetailResponse
import kotlinx.datetime.LocalDate
import kotlin.time.Duration.Companion.minutes


fun MovieDetailResponse.toMovieDetail(): MovieDetail = MovieDetail(
    id = requireNotNull(id),
    backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w500${it}" } ?: "",
    posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w200${it}" } ?: "",
    title = requireNotNull(title),
    releaseDate = LocalDate.parse(releaseDate.orEmpty()),
    genres = genres.orEmpty().map { it.toGenre() },
    duration = (runtime ?: 0).minutes,
    rating = voteAverage ?: 0f,
    overview = overview.orEmpty(),
    videos = videos?.results.orEmpty().map { it.toVideo() }
)