package com.amalhanaja.movietrek.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amalhanaja.movietrek.core.designsystem.R
import com.amalhanaja.movietrek.core.designsystem.component.ErrorComponent
import com.amalhanaja.movietrek.core.designsystem.component.LoadingComponent
import com.amalhanaja.movietrek.core.designsystem.component.RatingBarComponent
import com.amalhanaja.movietrek.core.designsystem.component.YoutubePlayerComponent
import com.amalhanaja.movietrek.core.designsystem.spacings
import com.amalhanaja.movietrek.core.model.MovieDetail
import com.amalhanaja.movietrek.core.model.Review
import java.util.Locale

private const val RATING_DIVIDER = 2

@Composable
fun MovieDetailRoute(
    title: String,
    id: Int,
    movieDetailViewModel: MovieDetailViewModel,
    onBack: () -> Unit,
) {
    val movieDetailUiState by movieDetailViewModel.movieDetailUiState.collectAsStateWithLifecycle()
    val reviews = movieDetailViewModel.reviews.collectAsLazyPagingItems()
    LaunchedEffect(key1 = Unit) {
        movieDetailViewModel.fetch(Locale.getDefault(), id)
    }
    MovieDetailScreen(
        title = title,
        movieDetailUiState = movieDetailUiState,
        reviews = reviews,
        onRetryGetDetail = { movieDetailViewModel.fetch(Locale.getDefault(), id) },
        onBack = onBack,
    )
}

@Composable
fun MovieDetailScreen(
    title: String,
    movieDetailUiState: MovieDetailUiState,
    reviews: LazyPagingItems<Review>,
    onRetryGetDetail: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBackIos, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (movieDetailUiState) {
            is MovieDetailUiState.Loading -> LoadingComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("state-loading"),
                text = stringResource(id = R.string.text_default_loading),
            )
            is MovieDetailUiState.Error -> ErrorComponent(
                title = movieDetailUiState.message,
                actionText = stringResource(id = R.string.text_general_error_action),
                onActionClick = { onRetryGetDetail() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("state-error"),
                illustration = {
                    Icon(Icons.Outlined.ErrorOutline, movieDetailUiState.message, tint = MaterialTheme.colorScheme.error)
                }
            )
            is MovieDetailUiState.Shown -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("state-shown")
            ) {
                val detail = movieDetailUiState.detail
                if (detail.backdropUrl.isNotBlank()) {
                    item {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f.div(9f)),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(detail.backdropUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = title,
                            contentScale = ContentScale.Crop,
                        )
                    }
                    item { Spacer(modifier = Modifier.height(MaterialTheme.spacings.l)) }
                    item {
                        MovieInformationSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MaterialTheme.spacings.xxl),
                            detail = detail,
                        )
                    }
                    if (detail.videos.isNotEmpty()) {
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = MaterialTheme.spacings.xxl, vertical = MaterialTheme.spacings.l),
                                text = "Videos",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                        item {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item { Spacer(modifier = Modifier.width(MaterialTheme.spacings.l)) }
                                items(items = detail.videos) { video ->
                                    YoutubePlayerComponent(
                                        videoId = video.videoId,
                                        modifier = Modifier
                                            .width(320.dp)
                                            .aspectRatio(16f.div(9f))
                                            .padding(horizontal = MaterialTheme.spacings.s),
                                        thumbnailUrl = video.thumbnailUrl,
                                    )
                                }
                                item { Spacer(modifier = Modifier.width(MaterialTheme.spacings.l)) }
                            }
                        }
                    }
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MaterialTheme.spacings.xxl, vertical = MaterialTheme.spacings.l),
                            text = "Reviews",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                    itemsIndexed(items = reviews) { index, review ->
                        if (index != 0) {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacings.l))
                        }
                        ReviewItem(review = review, modifier = Modifier.padding(horizontal = MaterialTheme.spacings.xxl))
                    }
                    if (reviews.itemCount == 0 && reviews.loadState.refresh is LoadState.NotLoading) {
                        item {
                            Text(
                                text = "We don't have any reviews for $title",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = MaterialTheme.spacings.xxl),
                            )
                        }
                    }
                    reviewLoadingErrorComponent(loadState = reviews.loadState.refresh, onTryAgain = { reviews.refresh() })
                    reviewLoadingErrorComponent(loadState = reviews.loadState.append, onTryAgain = { reviews.retry() })
                    item { Spacer(modifier = Modifier.height(MaterialTheme.spacings.xxl)) }
                }
            }
        }
    }
}

@Composable
private fun ReviewItem(review: Review?, modifier: Modifier = Modifier) {
    if (review == null) return
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = review.author.firstOrNull()?.toString().orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacings.l))
            Text(text = review.author, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacings.s))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBarComponent(rating = review.rating.div(RATING_DIVIDER))
            Spacer(modifier = Modifier.width(MaterialTheme.spacings.l))
            Text(text = review.timestamp.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacings.s))
        Text(
            text = review.content,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private fun LazyListScope.reviewLoadingErrorComponent(loadState: LoadState, onTryAgain: () -> Unit) {
    when (loadState) {
        is LoadState.Error -> item {
            ErrorComponent(
                title = loadState.error.message.orEmpty(),
                actionText = stringResource(id = R.string.text_general_error_action),
                onActionClick = onTryAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacings.l)
                    .testTag("state-review-error"),
                illustration = {
                    Icon(Icons.Outlined.ErrorOutline, loadState.error.message, tint = MaterialTheme.colorScheme.error)
                }
            )
        }
        is LoadState.Loading -> item {
            LoadingComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacings.l)
                    .testTag("state-review-loading"),
                text = stringResource(id = R.string.text_default_loading),
            )
        }
        else -> Unit
    }
}

@Composable
private fun MovieInformationSection(detail: MovieDetail, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.bodyMedium
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(0.75f)
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(detail.posterUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = detail.title,
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacings.l))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = detail.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacings.s))
                    Text(text = detail.genres.joinToString(" ??? ") { it.name })
                    Spacer(modifier = Modifier.height(MaterialTheme.spacings.s))
                    Text(text = "${detail.releaseDate.year} ??? ${detail.duration.inWholeMinutes} m")
                    Spacer(modifier = Modifier.height(MaterialTheme.spacings.s))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RatingBarComponent(rating = detail.rating.div(RATING_DIVIDER))
                        Spacer(modifier = Modifier.width(MaterialTheme.spacings.m))
                        Text(text = String.format("%.2f", detail.rating.div(RATING_DIVIDER)))
                    }
                }
            }
            if (detail.overview.isNotBlank()) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacings.l))
                Text(text = detail.overview)
            }
        }
    }
}