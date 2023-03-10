package com.amalhanaja.movietrek.genre

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalhanaja.movietrek.core.designsystem.component.ErrorComponent
import com.amalhanaja.movietrek.core.designsystem.component.LoadingComponent
import com.amalhanaja.movietrek.core.designsystem.spacings
import com.amalhanaja.movietrek.core.designsystem.R as DesignResource

private const val GENRE_GRID_ROW_COUNT = 2
private const val GENRE_CARD_HEIGHT_IN_DP = 48

@Composable
internal fun GenresRoute(
    genresViewModel: GenresViewModel,
    onGenreClick: (DisplayableGenre) -> Unit,
) {
    val genresUiState: GenresUiState by genresViewModel.genresUiState.collectAsStateWithLifecycle()

    // region effect
    LaunchedEffect(Unit) {
        genresViewModel.fetch()
    }
    // endregion

    // region composable
    GenresScreen(
        genresUiState = genresUiState,
        onGenreClick = onGenreClick,
        onErrorActionClick = genresViewModel::fetch
    )
}

@Composable
internal fun GenresScreen(
    genresUiState: GenresUiState,
    onGenreClick: (DisplayableGenre) -> Unit,
    onErrorActionClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_movie_genres)) }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        when (genresUiState) {
            is GenresUiState.Loading -> LoadingComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("state-loading"),
                text = stringResource(id = DesignResource.string.text_default_loading),
            )
            is GenresUiState.Shown -> GenresList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MaterialTheme.spacings.l)
                    .testTag("state-shown"),
                genres = genresUiState.genres,
                onItemClick = onGenreClick,
            )
            is GenresUiState.Error -> ErrorComponent(
                title = genresUiState.title,
                actionText = stringResource(id = DesignResource.string.text_general_error_action),
                onActionClick = onErrorActionClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .testTag("state-error"),
                illustration = { Icon(Icons.Outlined.ErrorOutline, genresUiState.title, tint = MaterialTheme.colorScheme.error) }
            )
        }
    }
}

@Composable
private fun GenresList(
    genres: List<DisplayableGenre>,
    onItemClick: (DisplayableGenre) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cardShape = MaterialTheme.shapes.medium
    LazyVerticalGrid(
        columns = GridCells.Fixed(GENRE_GRID_ROW_COUNT),
        modifier = modifier,
    ) {
        itemsIndexed(items = genres, key = { _, item -> item.id }) { _, item ->
            Box(modifier = Modifier.padding(MaterialTheme.spacings.s)) {
                Card(
                    modifier = Modifier.height(GENRE_CARD_HEIGHT_IN_DP.dp),
                    shape = cardShape,
                    onClick = { onItemClick(item) },
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}