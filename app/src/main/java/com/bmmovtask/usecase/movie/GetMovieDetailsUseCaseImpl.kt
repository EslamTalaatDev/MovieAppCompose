package com.bmmovtask.domain.usecase.movie

import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.model.movie.MovieDetails
import com.bmmovtask.data.remote.api.ApiResponse
import com.bmmovtask.data.remote.api.awaitApiResponse
import com.bmmovtask.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<MovieDetails> {
        return movieRepository.movieDetails(
            movieId = movieId,
            isoCode = deviceLanguage.languageCode
        ).awaitApiResponse()
    }
}