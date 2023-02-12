package com.amalhanaja.movietrek.core.tmdb.response


import com.google.gson.annotations.SerializedName

data class AuthorDetailResponse(
    @SerializedName(value = "avatar_path")
    val avatarPath: String? = null,
    @SerializedName(value = "name")
    val name: String? = null,
    @SerializedName(value = "rating")
    val rating: Int? = null,
    @SerializedName(value = "username")
    val username: String? = null,
)