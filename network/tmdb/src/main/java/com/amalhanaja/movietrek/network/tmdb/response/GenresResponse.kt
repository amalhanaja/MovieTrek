package com.amalhanaja.movietrek.network.tmdb.response

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName(value = "genres")
    val genres: List<GenreResponse>? = null,
)