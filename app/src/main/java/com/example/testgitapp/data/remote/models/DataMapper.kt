package com.example.testgitapp.data.remote.models

import com.example.testgitapp.data.remote.responces.GithubUserDetailResponse
import com.example.testgitapp.data.remote.responces.SearchGithubUserResponse
import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.model.DomainGithubUsers
import com.example.testgitapp.domain.model.DomainUser

interface DataMapper {

    fun toDomainUser(githubUser: GithubUserModel): DomainUser

    fun toDomainGithubUsers(githubUserResponse: SearchGithubUserResponse): DomainGithubUsers

    fun toDomainUserDetail(githubUserDetailResponse: GithubUserDetailResponse): DomainGitHubUserDetail

    class BaseDataMapper : DataMapper {

        override fun toDomainUser(githubUser: GithubUserModel): DomainUser =
            DomainUser(githubUser.id, githubUser.login, githubUser.avatarUrl)

        override fun toDomainGithubUsers(githubUserResponse: SearchGithubUserResponse): DomainGithubUsers =
            DomainGithubUsers(
                githubUserResponse.incompleteResults,
                githubUserResponse.items.map { toDomainUser(it) },
                githubUserResponse.totalCount,
            )

        override fun toDomainUserDetail(githubUserDetailResponse: GithubUserDetailResponse): DomainGitHubUserDetail =
            DomainGitHubUserDetail(
                githubUserDetailResponse.avatarUrl,
                githubUserDetailResponse.blog,
                githubUserDetailResponse.company,
                githubUserDetailResponse.createdAt,
                githubUserDetailResponse.eventsUrl,
                githubUserDetailResponse.followers,
                githubUserDetailResponse.following,
                githubUserDetailResponse.gistsUrl,
                githubUserDetailResponse.id,
                githubUserDetailResponse.location,
                githubUserDetailResponse.login,
                githubUserDetailResponse.name,
                githubUserDetailResponse.type,
                githubUserDetailResponse.updatedAt,
                githubUserDetailResponse.url,
            )
    }
}