package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.model.MovieItem
import com.amalhanaja.movietrek.core.tmdb.response.MovieResponse

internal fun MovieResponse.toMovieItem(): MovieItem = MovieItem(
    id = requireNotNull(id),
    posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w200${it}" }.orEmpty(),
    title = requireNotNull(title)
)