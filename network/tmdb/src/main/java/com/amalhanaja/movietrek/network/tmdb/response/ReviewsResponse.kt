package com.amalhanaja.movietrek.network.tmdb.response

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName(value = "results")
    val results: List<ReviewResponse>? = null,
)
