package com.bmmovtask.data.remote.api.movie

import com.bmmovtask.data.model.Config
import com.bmmovtask.data.model.DateParam
import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.model.GenresParam
import com.bmmovtask.data.model.WatchProvidersParam
import com.bmmovtask.data.model.movie.MovieDetails
import com.bmmovtask.data.model.movie.MoviesResponse
import retrofit2.Call

interface TmdbMoviesApiHelper {
    fun getConfig(): Call<Config>

    suspend fun discoverMovies(
        page: Int,
        isoCode: String = DeviceLanguage.default.languageCode,
        region: String = DeviceLanguage.default.region,
        genresParam: GenresParam = GenresParam(genres = emptyList()),
        watchProvidersParam: WatchProvidersParam = WatchProvidersParam(watchProviders = emptyList()),
        voteRange: ClosedFloatingPointRange<Float> = 0f..10f,
        fromReleaseDate: DateParam? = null,
        toReleaseDate: DateParam? = null
    ): MoviesResponse

    suspend fun getPopularMovies(
        page: Int,
        isoCode: String = DeviceLanguage.default.languageCode,
        region: String = DeviceLanguage.default.region,
    ): MoviesResponse

    suspend fun getUpcomingMovies(
        page: Int,
        isoCode: String = DeviceLanguage.default.languageCode,
        region: String = DeviceLanguage.default.region,
    ): MoviesResponse


    suspend fun getNowPlayingMovies(
        page: Int,
        isoCode: String = DeviceLanguage.default.languageCode,
        region: String = DeviceLanguage.default.region
    ): MoviesResponse

    fun getMovieDetails(
        movieId: Int,
        isoCode: String = DeviceLanguage.default.languageCode
    ): Call<MovieDetails>


}