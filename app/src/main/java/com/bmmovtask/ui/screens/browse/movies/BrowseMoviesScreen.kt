package com.bmmovtask.ui.screens.browse.movies

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.bmmovtask.R
import com.bmmovtask.data.model.movie.MovieType
import com.bmmovtask.ui.components.others.BmMoveBasicAppBar
import com.bmmovtask.ui.components.sections.BmMovePresentableGridSection
import com.bmmovtask.ui.screens.destinations.MovieDetailsScreenDestination
import com.bmmovtask.ui.screens.destinations.MovieScreenDestination
import com.bmmovtask.ui.theme.spacing
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class, ExperimentalLifecycleComposeApi::class)
@Destination(
    navArgsDelegate = BrowseMoviesScreenArgs::class,
    style = BrowseMoviesScreenTransitions::class
)
@Composable
fun AnimatedVisibilityScope.BrowseMoviesScreen(
    viewModel: BrowseMoviesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = { navigator.navigateUp() }
    val onClearDialogConfirmClick: () -> Unit = viewModel::onClearClicked
    val onMovieClicked = { movieId: Int ->
        val destination = MovieDetailsScreenDestination(
            movieId = movieId,
            startRoute = MovieScreenDestination.route
        )
        navigator.navigate(destination)
    }
    BrowseMoviesScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onClearDialogConfirmClicked = onClearDialogConfirmClick,
        onMovieClicked = onMovieClicked
    )
}

@Composable
fun BrowseMoviesScreenContent(
    uiState: BrowseMoviesScreenUIState,
    onBackClicked: () -> Unit,
    onClearDialogConfirmClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()
    val appbarTitle = when (uiState.selectedMovieType) {
        MovieType.NowPlaying -> stringResource(R.string.now_playing)
        MovieType.Upcoming -> stringResource(R.string.upcoming)
        MovieType.Popular -> stringResource(R.string.popular)
    }

    var showClearDialog by remember {
        mutableStateOf(false)
    }


    val dismissDialog = {
        showClearDialog = false
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BmMoveBasicAppBar(
            title = appbarTitle,
            action = {
                IconButton(
                    onClick = { onBackClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },

            )
        BmMovePresentableGridSection(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                top = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.large
            ),
            state = movies,
            onPresentableClick = onMovieClicked
        )
    }
}