package com.amalhanaja.movietrek.core.model

import kotlinx.datetime.LocalDate
import kotlin.time.Duration

class MovieDetail(
    val id: Int,
    val backdropUrl: String,
    val posterUrl: String,
    val title: String,
    val genres: List<Genre>,
    val releaseDate: LocalDate,
    val duration: Duration,
    val rating: Float,
    val overview: String,
    val videos: List<Video>,
)