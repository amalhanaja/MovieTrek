package com.amalhanaja.movietrek.genre

import androidx.compose.runtime.Stable
import com.amalhanaja.movietrek.core.model.Genre

@Stable
data class DisplayableGenre(
    val id: Int,
    val name: String,
)

fun Genre.toDisplayableGenre(): DisplayableGenre = DisplayableGenre(
    id = id,
    name = name
)