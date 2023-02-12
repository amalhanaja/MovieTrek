package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.model.Genre
import com.amalhanaja.movietrek.core.tmdb.response.GenreResponse

internal fun GenreResponse.toGenre(): Genre {
    return Genre(
        id = requireNotNull(id),
        name = requireNotNull(name),
    )
}