package com.amalhanaja.movietrek.core.tmdb.response

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName(value = "genres")
    val genres: List<GenreResponse>? = null,
)