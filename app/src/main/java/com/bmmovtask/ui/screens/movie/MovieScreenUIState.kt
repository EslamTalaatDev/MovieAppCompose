package com.bmmovtask.ui.screens.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.bmmovtask.data.model.DetailPresentable
import com.bmmovtask.data.model.Presentable
import com.bmmovtask.data.model.movie.RecentlyBrowsedMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class MovieScreenUIState(
    val moviesState: MoviesState,
    val recentlyBrowsed: Flow<PagingData<RecentlyBrowsedMovie>>
) {
    companion object {
        val default: MovieScreenUIState = MovieScreenUIState(
            moviesState = MoviesState.default,
            recentlyBrowsed = emptyFlow()
        )
    }
}

@Stable
data class MoviesState(
    val discover: Flow<PagingData<Presentable>>,
    val upcoming: Flow<PagingData<Presentable>>,
    val nowPlaying: Flow<PagingData<DetailPresentable>>
) {
    companion object {
        val default: MoviesState = MoviesState(
            discover = emptyFlow(),
            upcoming = emptyFlow(),
            nowPlaying = emptyFlow()
        )
    }
}

