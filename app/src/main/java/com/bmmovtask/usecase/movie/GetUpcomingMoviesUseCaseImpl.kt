package com.bmmovtask.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.model.Presentable
import com.bmmovtask.data.repository.movie.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetUpcomingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(deviceLanguage: DeviceLanguage): Flow<PagingData<Presentable>> {
        return movieRepository.upcomingMovies(deviceLanguage)
            .mapLatest { data -> data.map { it } }
    }
}