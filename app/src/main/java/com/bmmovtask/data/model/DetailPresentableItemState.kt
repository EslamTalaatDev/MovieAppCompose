package com.bmmovtask.data.model

sealed class DetailPresentableItemState {
    object Loading : DetailPresentableItemState()
    object Error : DetailPresentableItemState()
    data class Result(val presentable: DetailPresentable) : DetailPresentableItemState()
}