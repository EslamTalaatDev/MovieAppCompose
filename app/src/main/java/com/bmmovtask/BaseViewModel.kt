package com.bmmovtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bmmovtask.data.remote.api.ApiResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _error: MutableSharedFlow<String?> = MutableSharedFlow(replay = 0)
    val error: StateFlow<String?> = _error.asSharedFlow().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(10), null
    )

    protected fun <T> onError(response: ApiResponse.Exception<T>) {

        viewModelScope.launch {
            _error.emit(response.exception.localizedMessage)
        }
    }

    protected fun <T> onFailure(response: ApiResponse.Failure<T>) {
        viewModelScope.launch {
            _error.emit(response.apiError.statusMessage)
        }
    }
}