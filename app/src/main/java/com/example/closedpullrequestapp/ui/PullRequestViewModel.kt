package com.example.closedpullrequestapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.closedpullrequestapp.repo.PullRequestRepositoryImpl
import com.example.closedpullrequestapp.utils.AppConstant
import com.example.closedpullrequestapp.utils.Resource
import com.example.closedpullrequestapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(private val repo: PullRequestRepositoryImpl) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val uiState: StateFlow<UiEvent> = _uiState

    fun getList() {
        _uiState.value = UiEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repo.getPullRequest().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _uiState.value = UiEvent.Success(result.data)
                        }
                    }
                    is Resource.Error -> {
                        if (result.message.isNullOrEmpty()) {
                            _uiState.value =
                                UiEvent.Failure(AppConstant.SOME_THING_WENT_WRONG)
                        } else {
                            _uiState.value = UiEvent.Failure(result.message.toString())
                        }
                    }
                }

            }
        }
    }
}