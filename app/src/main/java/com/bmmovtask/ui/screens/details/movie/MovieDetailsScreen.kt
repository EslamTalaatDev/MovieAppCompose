package com.bmmovtask.ui.screens.details.movie

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bmmovtask.data.model.*
import com.bmmovtask.ui.components.sections.*
import com.bmmovtask.ui.screens.details.components.BmMoveMovieDetailsInfoSection
import com.bmmovtask.ui.theme.spacing
import com.bmmovtask.utils.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalLifecycleComposeApi::class)
@Destination(
    navArgsDelegate = MovieDetailsScreenArgs::class,
    style = MovieDetailsScreenTransitions::class
)
@Composable
fun AnimatedVisibilityScope.MovieDetailsScreen(
    viewModel: MovieDetailsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = { navigator.navigateUp() }
    MovieDetailsScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MovieDetailsScreenContent(
    uiState: MovieDetailsScreenUIState,
    onBackClicked: () -> Unit,
) {
    val density = LocalDensity.current

    val scrollState = rememberScrollState()


    var showErrorDialog by remember { mutableStateOf(false) }

    var topSectionHeight: Float? by remember { mutableStateOf(null) }
    val appbarHeight = density.run { 56.dp.toPx() }
    val topSectionScrollLimitValue: Float? = topSectionHeight?.minus(appbarHeight)

    LaunchedEffect(uiState.error) {
        showErrorDialog = uiState.error != null
    }

    BackHandler(showErrorDialog) {
        showErrorDialog = false
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            BmMovePresentableDetailsTopSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        topSectionHeight = coordinates.size.height.toFloat()
                    },
                presentable = uiState.movieDetails,
                scrollState = scrollState,
                scrollValueLimit = topSectionScrollLimitValue
            ) {
            }
            BmMoveMovieDetailsInfoSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .animateContentSize(),
                movieDetails = uiState.movieDetails,
                watchAtTime = uiState.additionalMovieDetailsInfo.watchAtTime,
            )

            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    insets = WindowInsets(bottom = MaterialTheme.spacing.medium)
                )
            )
        }
    }
}