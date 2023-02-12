package com.amalhanaja.movietrek.core.tmdb.response


import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName(value = "id")
    val id: String? = null,
    @SerializedName(value = "iso_3166_1")
    val iso31661: String? = null,
    @SerializedName(value = "iso_639_1")
    val iso6391: String? = null,
    @SerializedName(value = "key")
    val key: String? = null,
    @SerializedName(value = "name")
    val name: String? = null,
    @SerializedName(value = "official")
    val official: Boolean? = null,
    @SerializedName(value = "published_at")
    val publishedAt: String? = null,
    @SerializedName(value = "site")
    val site: String? = null,
    @SerializedName(value = "size")
    val size: Int? = null,
    @SerializedName(value = "type")
    val type: String? = null,
)