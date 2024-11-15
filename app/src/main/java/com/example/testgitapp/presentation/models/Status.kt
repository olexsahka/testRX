package com.example.testgitapp.presentation.models

import androidx.paging.PagingData


sealed class GithubUsersResult{
    data object Loading: GithubUsersResult()
    data class Success(val users: PagingData<UiModel>): GithubUsersResult()
    data class Error(val throwable: Throwable) : GithubUsersResult()
}