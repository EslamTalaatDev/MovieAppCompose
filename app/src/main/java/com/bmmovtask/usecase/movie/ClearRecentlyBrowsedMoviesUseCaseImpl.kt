package com.bmmovtask.domain.usecase.movie

import com.bmmovtask.data.repository.browsed.RecentlyBrowsedRepository
import javax.inject.Inject

class ClearRecentlyBrowsedMoviesUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) {
    operator fun invoke() {
        return recentlyBrowsedRepository.clearRecentlyBrowsedMovies()
    }
}