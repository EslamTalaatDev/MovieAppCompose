package com.bmmovtask.di

import android.content.Context
import com.bmmovtask.data.paging.ConfigDataSource
import com.bmmovtask.data.remote.api.movie.TmdbMoviesApiHelper
import com.bmmovtask.data.repository.browsed.RecentlyBrowsedRepository
import com.bmmovtask.data.repository.browsed.RecentlyBrowsedRepositoryImpl
import com.bmmovtask.data.repository.config.ConfigRepository
import com.bmmovtask.data.repository.config.ConfigRepositoryImpl
import com.bmmovtask.data.repository.movie.MovieRepository
import com.bmmovtask.data.repository.movie.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideConfigDataSource(
        @ApplicationContext context: Context,
        externalScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        apiMovieHelper: TmdbMoviesApiHelper,
    ): ConfigDataSource = ConfigDataSource(
        context = context,
        externalScope = externalScope,
        defaultDispatcher = dispatcher,
        apiMovieHelper = apiMovieHelper,
    )

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryBinds {
        @Binds
        @Singleton
        fun provideConfigRepository(impl: ConfigRepositoryImpl): ConfigRepository

        @Binds
        @Singleton
        fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository


        @Binds
        @Singleton
        fun bindsBrowsedRepository(impl: RecentlyBrowsedRepositoryImpl): RecentlyBrowsedRepository


    }
}