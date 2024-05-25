package com.bmmovtask.data.repository.browsed

import androidx.paging.PagingData
import com.bmmovtask.data.model.movie.MovieDetails
import com.bmmovtask.data.model.movie.RecentlyBrowsedMovie
import kotlinx.coroutines.flow.Flow

interface RecentlyBrowsedRepository {
    fun addRecentlyBrowsedMovie(movieDetails: MovieDetails)

    fun clearRecentlyBrowsedMovies()

    fun clearRecentlyBrowsedTvShows()

    fun recentlyBrowsedMovies(): Flow<PagingData<RecentlyBrowsedMovie>>


}