package com.amalhanaja.movietrek.core.tmdb.response


import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName(value = "adult")
    val adult: Boolean? = null,
    @SerializedName(value = "backdrop_path")
    val backdropPath: String? = null,
    @SerializedName(value = "budget")
    val budget: Long? = null,
    @SerializedName(value = "genres")
    val genres: List<GenreResponse>? = null,
    @SerializedName(value = "homepage")
    val homepage: String? = null,
    @SerializedName(value = "id")
    val id: Int? = null,
    @SerializedName(value = "imdb_id")
    val imdbId: String? = null,
    @SerializedName(value = "original_language")
    val originalLanguage: String? = null,
    @SerializedName(value = "original_title")
    val originalTitle: String? = null,
    @SerializedName(value = "overview")
    val overview: String? = null,
    @SerializedName(value = "popularity")
    val popularity: Double? = null,
    @SerializedName(value = "poster_path")
    val posterPath: String? = null,
    @SerializedName(value = "release_date")
    val releaseDate: String? = null,
    @SerializedName(value = "revenue")
    val revenue: Long? = null,
    @SerializedName(value = "runtime")
    val runtime: Int? = null,
    @SerializedName(value = "status")
    val status: String? = null,
    @SerializedName(value = "tagline")
    val tagline: String? = null,
    @SerializedName(value = "title")
    val title: String? = null,
    @SerializedName(value = "videos")
    val videos: VideosResponse? = null,
    @SerializedName(value = "vote_average")
    val voteAverage: Double? = null,
    @SerializedName(value = "vote_count")
    val voteCount: Int? = null,
)