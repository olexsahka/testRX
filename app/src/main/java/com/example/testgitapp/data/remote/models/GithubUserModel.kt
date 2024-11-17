package com.example.testgitapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class GithubUserModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val login: String,
)

