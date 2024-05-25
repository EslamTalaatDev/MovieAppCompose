package com.bmmovtask.ui.screens.details.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bmmovtask.BaseViewModel
import com.bmmovtask.data.model.DeviceLanguage
import com.bmmovtask.data.model.WatchProviders
import com.bmmovtask.data.model.movie.MovieCollection
import com.bmmovtask.data.model.movie.MovieDetails
import com.bmmovtask.data.remote.api.onException
import com.bmmovtask.data.remote.api.onFailure
import com.bmmovtask.data.remote.api.onSuccess
import com.bmmovtask.domain.usecase.GetDeviceLanguageUseCaseImpl
import com.bmmovtask.domain.usecase.movie.GetMovieCollectionUseCaseImpl
import com.bmmovtask.domain.usecase.movie.GetMovieDetailsUseCaseImpl
import com.bmmovtask.ui.screens.destinations.MovieDetailsScreenDestination
import com.bmmovtask.usecase.movie.AddRecentlyBrowsedMovieUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCaseImpl,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCaseImpl,
    private val getMovieCollectionUseCase: GetMovieCollectionUseCaseImpl,
    private val addRecentlyBrowsedMovieUseCase: AddRecentlyBrowsedMovieUseCaseImpl,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val navArgs: MovieDetailsScreenArgs =
        MovieDetailsScreenDestination.argsFrom(savedStateHandle)
    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCase()
    private val watchAtTime: MutableStateFlow<Date?> = MutableStateFlow(null)
    private val _movieDetails: MutableStateFlow<MovieDetails?> = MutableStateFlow(null)
    private val movieDetails: StateFlow<MovieDetails?> =
        _movieDetails.onEach { movieDetails ->
            movieDetails?.let(addRecentlyBrowsedMovieUseCase::invoke)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(10), null)
    private val movieCollection: MutableStateFlow<MovieCollection?> = MutableStateFlow(null)

    private val watchProviders: MutableStateFlow<WatchProviders?> = MutableStateFlow(null)
    private val reviewsCount: MutableStateFlow<Int> = MutableStateFlow(0)


    private val additionalInfo: StateFlow<AdditionalMovieDetailsInfo> = combine(
        watchAtTime, watchProviders, reviewsCount
    ) { watchAtTime, watchProviders, reviewsCount ->
        AdditionalMovieDetailsInfo(
            watchAtTime = watchAtTime,
            watchProviders = watchProviders,
            reviewsCount = reviewsCount
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10),
        AdditionalMovieDetailsInfo.default
    )


    val uiState: StateFlow<MovieDetailsScreenUIState> = combine(
        movieDetails, additionalInfo, error
    ) { details, additionalInfo, error ->
        MovieDetailsScreenUIState(
            startRoute = navArgs.startRoute,
            movieDetails = details,
            additionalMovieDetailsInfo = additionalInfo,
            error = error
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        MovieDetailsScreenUIState.getDefault(navArgs.startRoute)
    )

    init {
        getMovieInfo()
    }

    private fun getMovieInfo() {
        viewModelScope.launch {
            val movieId = navArgs.movieId

            deviceLanguage.collectLatest { deviceLanguage ->
                launch {
                    getMovieDetails(movieId, deviceLanguage)
                }
            }
        }

        startRefreshingWatchAtTime()
    }

    private fun startRefreshingWatchAtTime() {
        viewModelScope.launch {
            _movieDetails.collectLatest { details ->
                while (isActive) {
                    details?.runtime?.let { runtime ->
                        if (runtime > 0) {
                            runtime.minutes.toComponents { hours, minutes, _, _ ->
                                val time = Calendar.getInstance().apply {
                                    time = Date()

                                    add(Calendar.HOUR, hours.toInt())
                                    add(Calendar.MINUTE, minutes)
                                }.time

                                watchAtTime.emit(time)
                            }
                        }
                    }

                    delay(10.seconds)
                }
            }
        }
    }


    private suspend fun getMovieDetails(movieId: Int, deviceLanguage: DeviceLanguage) {
        getMovieDetailsUseCase(
            movieId = movieId,
            deviceLanguage = deviceLanguage
        ).onSuccess {
            viewModelScope.launch {
                val movieDetails = data
                _movieDetails.emit(movieDetails)

                data?.collection?.id?.let { collectionId ->
                    getMovieCollection(
                        collectionId = collectionId,
                        deviceLanguage = deviceLanguage
                    )
                }
            }
        }.onFailure {
            onFailure(this)
        }.onException {
            onError(this)
        }
    }

    private suspend fun getMovieCollection(collectionId: Int, deviceLanguage: DeviceLanguage) {
        getMovieCollectionUseCase(
            collectionId = collectionId,
            deviceLanguage = deviceLanguage
        ).onSuccess {
            viewModelScope.launch {
                movieCollection.emit(data)
            }
        }.onFailure {
            onFailure(this)
        }.onException {
            onError(this)
        }
    }


}