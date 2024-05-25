package com.bmmovtask.usecase.movie

import com.bmmovtask.data.model.movie.MovieDetails
import com.bmmovtask.data.repository.browsed.RecentlyBrowsedRepository
import javax.inject.Inject

class AddRecentlyBrowsedMovieUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) {
    operator fun invoke(details: MovieDetails) {
        return recentlyBrowsedRepository.addRecentlyBrowsedMovie(details)
    }
}
