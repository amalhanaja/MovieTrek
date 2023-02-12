package com.amalhanaja.movietrek.network.tmdb.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName(value = "id")
    val id: Int? = null,
    @SerializedName(value = "name")
    val name: String? = null,
)