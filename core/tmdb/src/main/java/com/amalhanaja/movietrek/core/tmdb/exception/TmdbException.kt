package com.amalhanaja.movietrek.core.tmdb.exception

class TmdbException(
    val code: String?,
    override val message: String?,
    override val cause: Throwable?,
) : RuntimeException(message, cause)