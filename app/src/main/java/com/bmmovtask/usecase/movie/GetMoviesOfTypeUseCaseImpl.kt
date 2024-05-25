package com.bmmovtask.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.model.Presentable
import com.bmmovtask.data.model.movie.MovieType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetMoviesOfTypeUseCaseImpl @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCaseImpl,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCaseImpl,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCaseImpl
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        type: MovieType,
        deviceLanguage: DeviceLanguage
    ): Flow<PagingData<Presentable>> {
        return when (type) {
            MovieType.NowPlaying -> getNowPlayingMoviesUseCase(deviceLanguage, false)
            MovieType.Upcoming -> getUpcomingMoviesUseCase(deviceLanguage)
            MovieType.Popular -> getPopularMoviesUseCase(deviceLanguage)
        }.mapLatest { data -> data.map { it } }
    }
}