package com.amalhanaja.movietrek.discovermovie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amalhanaja.movietrek.core.designsystem.component.ErrorComponent
import com.amalhanaja.movietrek.core.designsystem.component.LoadingComponent
import com.amalhanaja.movietrek.core.designsystem.spacings
import com.amalhanaja.movietrek.core.model.MovieItem
import java.util.Locale
import com.amalhanaja.movietrek.core.designsystem.R as DesignResource


@Composable
internal fun DiscoverMovieRoute(
    genre: String,
    genreId: Int,
    discoverMovieViewModel: DiscoverMovieViewModel,
    onBack: () -> Unit,
) {
    val discoverMovieUiState by discoverMovieViewModel.discoverMovieUiState.collectAsStateWithLifecycle()

    DiscoverMovieScreen(
        genre = genre,
        discoverMovieUiState = discoverMovieUiState,
        movieItems = discoverMovieViewModel.movieItems,
        onLoad = { discoverMovieViewModel.load(Locale.getDefault(), listOf(genreId)) },
        onRetry = discoverMovieViewModel::retry,
        onBack = onBack
    )
}

@Composable
internal fun DiscoverMovieScreen(
    genre: String,
    discoverMovieUiState: DiscoverMovieUiState,
    movieItems: List<MovieItem>,
    onLoad: () -> Unit,
    onRetry: () -> Unit,
    onBack: () -> Unit,
    lazyGridState: LazyGridState = rememberLazyGridState(),
) {

    val shouldLoadNextPage = remember {
        derivedStateOf {

            // get last visible item
            val lastVisibleItem = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true

            // Check if last visible item is the last item in the list
            lastVisibleItem.index == lazyGridState.layoutInfo.totalItemsCount - 1
        }
    }
    LaunchedEffect(key1 = shouldLoadNextPage.value) {
        onLoad()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(genre) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBackIos, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (discoverMovieUiState) {
            is DiscoverMovieUiState.Loading -> LoadingComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("state-loading"),
                text = stringResource(id = DesignResource.string.text_default_loading),
            )
            is DiscoverMovieUiState.ErrorLoad -> ErrorComponent(
                title = discoverMovieUiState.message,
                actionText = stringResource(id = DesignResource.string.text_general_error_action),
                onActionClick = { onRetry() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("state-error"),
                illustration = {
                    Icon(Icons.Outlined.ErrorOutline, discoverMovieUiState.message, tint = MaterialTheme.colorScheme.error)
                }
            )
            else -> {
                LazyVerticalGrid(
                    state = lazyGridState,
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .testTag("state-loaded")
                ) {
                    itemsIndexed(movieItems, key = { _, item -> item.id }) { _, item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.spacings.s)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.75f)
                                    .clip(MaterialTheme.shapes.medium),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.posterUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = item.title,
                                contentScale = ContentScale.Crop,
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacings.s))
                            Text(text = item.title, style = MaterialTheme.typography.labelLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                    }
                    if (discoverMovieUiState is DiscoverMovieUiState.LoadingMoreItems) {
                        item {
                            LoadingComponent(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .testTag("state-item-loading"),
                                text = stringResource(id = DesignResource.string.text_default_loading),
                            )
                        }
                    }
                    (discoverMovieUiState as? DiscoverMovieUiState.ErrorLoadMoreItem)?.let {
                        item {
                            ErrorComponent(
                                title = discoverMovieUiState.message,
                                actionText = stringResource(id = DesignResource.string.text_general_error_action),
                                onActionClick = { onRetry() },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .testTag("state-item-error"),
                                illustration = {
                                    Icon(Icons.Outlined.ErrorOutline, discoverMovieUiState.message, tint = MaterialTheme.colorScheme.error)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}