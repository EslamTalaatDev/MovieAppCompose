package com.bmmovtask.data.repository.browsed

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bmmovtask.data.local.db.movie.RecentlyBrowsedMoviesDao
import com.bmmovtask.data.model.movie.MovieDetails
import com.bmmovtask.data.model.movie.RecentlyBrowsedMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecentlyBrowsedRepositoryImpl @Inject constructor(
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val recentlyBrowsedMoviesDao: RecentlyBrowsedMoviesDao,
) : RecentlyBrowsedRepository {
    private companion object {
        const val maxItems = 100
    }

    override fun addRecentlyBrowsedMovie(movieDetails: MovieDetails) {
        externalScope.launch(defaultDispatcher) {
            val recentlyBrowsedMovie = movieDetails.run {
                RecentlyBrowsedMovie(
                    id = id,
                    posterPath = posterPath,
                    title = title,
                    addedDate = Date()
                )
            }

            recentlyBrowsedMoviesDao.deleteAndAdd(
                recentlyBrowsedMovie,
                maxItems = maxItems
            )
        }
    }

    override fun clearRecentlyBrowsedMovies() {
        externalScope.launch(defaultDispatcher) {
            recentlyBrowsedMoviesDao.clear()
        }
    }

    override fun clearRecentlyBrowsedTvShows() {
        externalScope.launch(defaultDispatcher) {
        }
    }

    override fun recentlyBrowsedMovies(): Flow<PagingData<RecentlyBrowsedMovie>> = Pager(
        PagingConfig(pageSize = 20)
    ) {
        recentlyBrowsedMoviesDao.recentBrowsedMovie().asPagingSourceFactory()()
    }.flow.flowOn(defaultDispatcher)


}