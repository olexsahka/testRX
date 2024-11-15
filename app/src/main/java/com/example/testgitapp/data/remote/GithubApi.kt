package com.example.testgitapp.data.remote

import com.example.testgitapp.data.remote.models.GithubUserListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("/search/users")
    fun getGithubUsers(
        @Query("q") query: String = "repos:>1",
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): Single<GithubUserListResponse>
}