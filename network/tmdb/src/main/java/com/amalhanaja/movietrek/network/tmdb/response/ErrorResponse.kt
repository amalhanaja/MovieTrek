package com.amalhanaja.movietrek.network.tmdb.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName(value = "status_code")
    val statusCode: Int? = null,
    @SerializedName(value = "status_message")
    val statusMessage: String? = null,
)