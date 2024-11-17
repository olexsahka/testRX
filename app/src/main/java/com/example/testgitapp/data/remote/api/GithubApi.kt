package com.example.testgitapp.data.remote.api

import com.example.testgitapp.data.remote.responces.GithubUserDetailResponse
import com.example.testgitapp.data.remote.responces.SearchGithubUserResponse
import com.example.testgitapp.data.remote.responces.GithubUsersDefaultResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("/users")
    fun getGithubUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Single<Response<GithubUsersDefaultResponse>>

    @GET("search/users")
    fun searchGithubUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ):Single<SearchGithubUserResponse>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") userName: String,
    ): Single<Response<GithubUserDetailResponse>>
}
