package com.amalhanaja.movietrek.core.tmdb.response

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName(value = "results")
    val results: List<MovieResponse>? = null,
)