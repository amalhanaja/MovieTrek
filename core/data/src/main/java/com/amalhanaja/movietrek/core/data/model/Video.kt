package com.amalhanaja.movietrek.core.data.model

import com.amalhanaja.movietrek.core.model.Video
import com.amalhanaja.movietrek.core.tmdb.response.VideoResponse

internal fun VideoResponse.toVideo(): Video = Video(
    videoUrl = "https://www.youtube.com/watch?v=$key",
    thumbnailUrl = "https://img.youtube.com/vi/$key/0.jpg",
    title = name.orEmpty(),
)