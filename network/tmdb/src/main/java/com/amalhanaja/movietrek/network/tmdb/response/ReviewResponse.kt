package com.amalhanaja.movietrek.network.tmdb.response


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName(value = "author")
    val author: String? = null,
    @SerializedName(value = "author_details")
    val authorDetails: AuthorDetailResponse? = null,
    @SerializedName(value = "content")
    val content: String? = null,
    @SerializedName(value = "created_at")
    val createdAt: String? = null,
    @SerializedName(value = "id")
    val id: String? = null,
    @SerializedName(value = "updated_at")
    val updatedAt: String? = null,
    @SerializedName(value = "url")
    val url: String? = null,
)