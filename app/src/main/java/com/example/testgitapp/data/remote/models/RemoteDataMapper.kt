package com.example.testgitapp.data.remote.models

import com.example.testgitapp.data.remote.responces.GithubUserDetailResponse
import com.example.testgitapp.data.remote.responces.SearchGithubUserResponse
import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.model.DomainGithubUsers
import com.example.testgitapp.domain.model.DomainUser

interface RemoteDataMapper {

    fun toDomainUser(githubUser: GithubUserModel): DomainUser

    fun toDomainGithubUsers(githubUserResponse: SearchGithubUserResponse): DomainGithubUsers

    fun toDomainUserDetail(githubUserDetailResponse: GithubUserDetailResponse): DomainGitHubUserDetail

    class BaseRemoteDataMapper : RemoteDataMapper {

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
                avatarUrl = githubUserDetailResponse.avatarUrl,
                blog = githubUserDetailResponse.blog,
                company = githubUserDetailResponse.company,
                createdAt = githubUserDetailResponse.createdAt,
                eventsUrl = githubUserDetailResponse.eventsUrl,
                followers = githubUserDetailResponse.followers,
                following = githubUserDetailResponse.following,
                gistsUrl = githubUserDetailResponse.gistsUrl,
                id = githubUserDetailResponse.id,
                location = githubUserDetailResponse.location,
                login = githubUserDetailResponse.login,
                name = githubUserDetailResponse.name,
                type = githubUserDetailResponse.type,
                updatedAt = githubUserDetailResponse.updatedAt,
                url = githubUserDetailResponse.url,
            )
    }
}