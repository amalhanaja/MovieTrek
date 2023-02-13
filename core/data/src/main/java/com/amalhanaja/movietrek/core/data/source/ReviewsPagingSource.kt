package com.amalhanaja.movietrek.core.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amalhanaja.movietrek.core.data.model.toReview
import com.amalhanaja.movietrek.core.model.Review
import com.amalhanaja.movietrek.core.tmdb.TmdbClient
import java.util.Locale

class ReviewsPagingSource(
    private val tmdbClient: TmdbClient,
    private val locale: Locale,
    private val id: Int,
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: 1
            val response = tmdbClient.getMovieReviews(locale, id, page)
            LoadResult.Page(
                data = response.map { it.toReview() },
                nextKey = page.plus(1).takeIf { response.count() >= params.loadSize },
                prevKey = null,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}