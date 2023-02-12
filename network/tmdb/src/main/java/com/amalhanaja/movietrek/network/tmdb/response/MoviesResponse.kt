package com.amalhanaja.movietrek.network.tmdb.response

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName(value = "results")
    val results: List<MovieResponse>? = null,
)