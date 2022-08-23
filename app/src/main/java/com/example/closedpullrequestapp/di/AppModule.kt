package com.example.closedpullrequestapp.di

import com.example.closedpullrequestapp.data.remote.NetworkApi
import com.example.closedpullrequestapp.repo.PullRequestRepository
import com.example.closedpullrequestapp.repo.PullRequestRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.github.com/"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesGitPullApi(okHttpClient: OkHttpClient): NetworkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(NetworkApi::class.java)

    @Singleton
    @Provides
    fun providesRepo(api : NetworkApi) : PullRequestRepository =
        PullRequestRepositoryImpl(api)


}