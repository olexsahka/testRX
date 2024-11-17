package com.example.testgitapp.domain.repository

import com.example.testgitapp.domain.model.Result
import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.model.DomainGithubUsers
import com.example.testgitapp.presentation.adapters.UserViewHolder
import io.reactivex.rxjava3.core.Single

interface GithubRepository {
    fun getGithubUserList(page: Int, perPage: Int) : Single<Result<DomainGithubUsers>>
    fun searchUsersByQuery(query:String, page: Int, perPage: Int) : Single<Result<DomainGithubUsers>>
    fun getUsersDetail(name : String): Single<Result<DomainGitHubUserDetail>>
}