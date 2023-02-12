package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.model.Review
import com.amalhanaja.movietrek.core.tmdb.response.ReviewResponse
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ReviewResponse.toReview(): Review = Review(
    author = requireNotNull(author),
    rating = authorDetails?.rating?.toFloat() ?: 0f,
    avatarUrl = authorDetails?.avatarPath?.let { "$it" }.orEmpty(),
    content = content.orEmpty(),
    timestamp = Instant.parse(updatedAt.orEmpty()).toLocalDateTime(TimeZone.currentSystemDefault())
)