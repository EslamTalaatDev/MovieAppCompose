package com.bmmovtask.ui.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.domain.usecase.GetDeviceLanguageUseCaseImpl
import com.bmmovtask.domain.usecase.movie.GetNowPlayingMoviesUseCaseImpl
import com.bmmovtask.domain.usecase.movie.GetPopularMoviesUseCaseImpl
import com.bmmovtask.domain.usecase.movie.GetRecentlyBrowsedMoviesUseCaseImpl
import com.bmmovtask.domain.usecase.movie.GetUpcomingMoviesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieScreenViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCaseImpl,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCaseImpl,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCaseImpl,
    private val getTrendingMoviesUseCase: GetPopularMoviesUseCaseImpl,
    private val getRecentlyBrowsedMoviesUseCase: GetRecentlyBrowsedMoviesUseCaseImpl
) : ViewModel() {
    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCase()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val moviesState: StateFlow<MoviesState> = deviceLanguage.mapLatest { deviceLanguage ->
        MoviesState(
            nowPlaying = getNowPlayingMoviesUseCase(deviceLanguage, true).cachedIn(viewModelScope),
            discover = getTrendingMoviesUseCase(deviceLanguage).cachedIn(viewModelScope),
            upcoming = getUpcomingMoviesUseCase(deviceLanguage).cachedIn(viewModelScope),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10), MoviesState.default)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<MovieScreenUIState> = moviesState.mapLatest { moviesState ->
        MovieScreenUIState(
            moviesState = moviesState,
            recentlyBrowsed = getRecentlyBrowsedMoviesUseCase().cachedIn(viewModelScope)
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, MovieScreenUIState.default)
}