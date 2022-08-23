package com.example.closedpullrequestapp.repo

import com.example.closedpullrequestapp.data.remote.dto.GitPullResponse
import com.example.closedpullrequestapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PullRequestRepository {

    suspend fun getPullRequest(): Flow<Resource<GitPullResponse>>
}