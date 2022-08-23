package com.example.closedpullrequestapp.data.remote

import com.example.closedpullrequestapp.data.remote.dto.GitPullResponse
import retrofit2.Response
import retrofit2.http.GET

interface NetworkApi {
    @GET("repos/Gautham245/Gautham245.github.io/pulls?state=closed")
    suspend fun getGitPullRequest(): Response<GitPullResponse>
}
