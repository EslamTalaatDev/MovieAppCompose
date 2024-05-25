package com.bmmovtask.data.remote.api

data class ApiError(
    val errorCode: Int,
    val statusCode: Int?,
    val statusMessage: String?
)