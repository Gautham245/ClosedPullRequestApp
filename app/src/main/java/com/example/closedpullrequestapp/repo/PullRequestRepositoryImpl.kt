package com.example.closedpullrequestapp.repo

import com.example.closedpullrequestapp.data.remote.NetworkApi
import com.example.closedpullrequestapp.data.remote.dto.GitPullResponse
import com.example.closedpullrequestapp.utils.AppConstant
import com.example.closedpullrequestapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PullRequestRepositoryImpl @Inject constructor(private val api: NetworkApi) :
    PullRequestRepository {
    override suspend fun getPullRequest(): Flow<Resource<GitPullResponse>> {
        return flow {
            try {

                val response = api.getGitPullRequest()
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    result?.let {
                        emit(Resource.Success(result))
                    }
                } else
                    emit(
                        Resource.Error(
                            response.message() + " code : " + response.code()
                                .toString()
                        )
                    )

            } catch (e: HttpException) {
                emit(Resource.Error(AppConstant.OFFLINE))
            } catch (e: IOException) {
                emit(Resource.Error(AppConstant.OFFLINE))
            }
        }

    }
}