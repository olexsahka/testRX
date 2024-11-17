package com.example.testgitapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class GithubUserListResponse(
    @SerializedName("total_count") var totalCount: Long,
    @SerializedName("incomplete_results") var incompleteResults: Boolean,
    @SerializedName("items") var items: List<GithubUser>
)