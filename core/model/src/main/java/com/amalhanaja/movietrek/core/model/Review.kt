package com.amalhanaja.movietrek.core.model

import kotlinx.datetime.LocalDateTime

data class Review(
    val author: String,
    val rating: Float,
    val avatarUrl: String,
    val content: String,
    val timestamp: LocalDateTime,
)
