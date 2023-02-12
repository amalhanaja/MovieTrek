package com.amalhanaja.movietrek.network.tmdb.response

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName(value = "results")
    val results: List<VideoResponse>? = null,
)