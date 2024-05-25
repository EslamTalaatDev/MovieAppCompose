package com.bmmovtask.ui.screens.movie

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.bmmovtask.MainViewModel
import com.bmmovtask.R
import com.bmmovtask.data.model.movie.MovieType
import com.bmmovtask.ui.components.sections.BmMovePresentableSection
import com.bmmovtask.ui.screens.destinations.BrowseMoviesScreenDestination
import com.bmmovtask.ui.screens.destinations.MovieDetailsScreenDestination
import com.bmmovtask.ui.screens.destinations.MovieScreenDestination
import com.bmmovtask.ui.theme.spacing
import com.bmmovtask.utils.isAnyRefreshing
import com.bmmovtask.utils.refreshAll
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalLifecycleComposeApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun AnimatedVisibilityScope.MovieScreen(
    mainViewModel: MainViewModel,
    viewModel: MovieScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        mainViewModel.sameBottomBarRoute.collectLatest { sameRoute ->
            if (sameRoute == MovieScreenDestination.route) {
                scrollState.animateScrollTo(0)
            }
        }
    }
    val onMovieClicked = { movieId: Int ->
        val destination = MovieDetailsScreenDestination(
            movieId = movieId,
            startRoute = MovieScreenDestination.route
        )

        navigator.navigate(destination)
    }

    val onBrowseMoviesClicked = { type: MovieType ->
        navigator.navigate(BrowseMoviesScreenDestination(type))
    }


    MoviesScreenContent(
        uiState = uiState,
        scrollState = scrollState,
        onMovieClicked = onMovieClicked,
        onBrowseMoviesClicked = onBrowseMoviesClicked,
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MoviesScreenContent(
    uiState: MovieScreenUIState,
    scrollState: ScrollState,
    onMovieClicked: (movieId: Int) -> Unit,
    onBrowseMoviesClicked: (type: MovieType) -> Unit,
) {
    val context = LocalContext.current
    val discoverLazyItems = uiState.moviesState.discover.collectAsLazyPagingItems()
    val upcomingLazyItems = uiState.moviesState.upcoming.collectAsLazyPagingItems()
    val nowPlayingLazyItems = uiState.moviesState.nowPlaying.collectAsLazyPagingItems()


    var showExitDialog by remember {
        mutableStateOf(false)
    }
    val dismissDialog = {
        showExitDialog = false
    }
    BackHandler {
        showExitDialog = true
    }


    val isRefreshing by derivedStateOf {
        listOf(
            discoverLazyItems,
            upcomingLazyItems,
            nowPlayingLazyItems
        ).isAnyRefreshing()
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    val refreshAllPagingData = {
        listOf(
            discoverLazyItems,
            upcomingLazyItems,
            nowPlayingLazyItems
        ).refreshAll()
    }

    LaunchedEffect(isRefreshing) {
        swipeRefreshState.isRefreshing = isRefreshing
    }

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = swipeRefreshState,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = MaterialTheme.spacing.large),
                state = state,
                refreshTriggerDistance = trigger,
                fade = true,
                scale = true,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            )
        },
        onRefresh = refreshAllPagingData
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            BmMovePresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(R.string.now_playing),
                state = nowPlayingLazyItems,
                onPresentableClick = onMovieClicked,
                onMoreClick = {
                    onBrowseMoviesClicked(MovieType.NowPlaying)
                }
            )

            BmMovePresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(R.string.popular),
                state = discoverLazyItems,
                onMoreClick = {
                    onBrowseMoviesClicked(MovieType.Popular)
                },
                onPresentableClick = onMovieClicked,
            )
            BmMovePresentableSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                title = stringResource(R.string.upcoming),
                state = upcomingLazyItems,
                onPresentableClick = onMovieClicked,
                onMoreClick = {
                    onBrowseMoviesClicked(MovieType.Upcoming)
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }
}