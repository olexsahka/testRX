package com.example.testgitapp.presentation.models

import androidx.paging.PagingData


sealed class GithubUserDetailsResult{
    data object Loading: GithubUserDetailsResult()
    data class Success(val detail: DetailUiModel): GithubUserDetailsResult()
    data class Error(val throwable: Throwable) : GithubUserDetailsResult()
}