package com.example.testgitapp.data.remote.responces

import com.example.testgitapp.data.remote.models.GithubUserModel
import com.google.gson.annotations.SerializedName

data class SearchGithubUserResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<GithubUserModel>,
    @SerializedName("total_count")
    val totalCount: Int,
)

