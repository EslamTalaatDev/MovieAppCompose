package com.bmmovtask.data.repository.movie

import androidx.paging.PagingData
import com.bmmovtask.data.model.CollectionResponse
import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.model.movie.MovieDetailEntity
import com.bmmovtask.data.model.movie.MovieDetails
import com.bmmovtask.data.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface MovieRepository {

    fun popularMovies(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<MovieEntity>>

    fun upcomingMovies(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<MovieEntity>>

    fun nowPlayingMovies(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<MovieDetailEntity>>


    fun movieDetails(
        movieId: Int,
        isoCode: String = DeviceLanguage.default.languageCode
    ): Call<MovieDetails>


    fun collection(
        collectionId: Int,
        isoCode: String = DeviceLanguage.default.languageCode
    ): Call<CollectionResponse>

}