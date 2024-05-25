package com.bmmovtask.ui.screens.browse.movies

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.bmmovtask.data.model.Presentable
import com.bmmovtask.data.model.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class BrowseMoviesScreenUIState(
    val selectedMovieType: MovieType,
    val movies: Flow<PagingData<Presentable>>,
) {
    companion object {
        fun getDefault(selectedMovieType: MovieType): BrowseMoviesScreenUIState {
            return BrowseMoviesScreenUIState(
                selectedMovieType = selectedMovieType,
                movies = emptyFlow(),
            )
        }
    }
}