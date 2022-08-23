package com.example.closedpullrequestapp.utils

import com.example.closedpullrequestapp.data.remote.dto.GitPullResponse

sealed class UiEvent {
    class Success(val result: GitPullResponse?) : UiEvent()
    class Failure(val errorText: String) : UiEvent()
    object Loading : UiEvent()
    object Empty : UiEvent()
}
