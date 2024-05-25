package com.bmmovtask.data.repository.config

import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.model.Genre
import com.bmmovtask.data.model.ProviderSource
import com.bmmovtask.utils.ImageUrlParser
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun isInitialized(): Flow<Boolean>

    fun updateLocale()

    fun getSpeechToTextAvailable(): Flow<Boolean>

    fun getCameraAvailable(): Flow<Boolean>

    fun getDeviceLanguage(): Flow<DeviceLanguage>

    fun getImageUrlParser(): Flow<ImageUrlParser?>

    fun getMoviesGenres(): Flow<List<Genre>>

    fun getTvShowGenres(): Flow<List<Genre>>

    fun getAllMoviesWatchProviders(): Flow<List<ProviderSource>>

    fun getAllTvShowWatchProviders(): Flow<List<ProviderSource>>
}