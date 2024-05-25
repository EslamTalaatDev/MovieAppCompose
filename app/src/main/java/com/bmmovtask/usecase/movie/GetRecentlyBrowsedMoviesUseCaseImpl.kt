package com.bmmovtask.domain.usecase.movie

import androidx.paging.PagingData
import com.bmmovtask.data.model.movie.RecentlyBrowsedMovie
import com.bmmovtask.data.repository.browsed.RecentlyBrowsedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentlyBrowsedMoviesUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository,
) {
    operator fun invoke(): Flow<PagingData<RecentlyBrowsedMovie>> {
        return recentlyBrowsedRepository.recentlyBrowsedMovies()
    }
}