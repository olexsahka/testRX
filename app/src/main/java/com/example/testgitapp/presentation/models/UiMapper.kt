package com.example.testgitapp.presentation.models

import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.model.DomainUser

interface UiMapper {

    fun toGithubUserUiModel(domainUser: DomainUser): UserUiModel

    fun toGithubUserUiDetailModel(domainGitHubUserDetail: DomainGitHubUserDetail): DetailUiModel

    class BaseUiMapper : UiMapper {

        override fun toGithubUserUiModel(domainUser: DomainUser): UserUiModel =
            UserUiModel(domainUser.id, domainUser.name, domainUser.avatarUrl)

        override fun toGithubUserUiDetailModel(domainGitHubUserDetail: DomainGitHubUserDetail): DetailUiModel =
            DetailUiModel(
                domainGitHubUserDetail.avatarUrl,
                domainGitHubUserDetail.blog,
                domainGitHubUserDetail.company,
                domainGitHubUserDetail.createdAt,
                domainGitHubUserDetail.eventsUrl,
                domainGitHubUserDetail.followers,
                domainGitHubUserDetail.following,
                domainGitHubUserDetail.gistsUrl,
                domainGitHubUserDetail.id,
                domainGitHubUserDetail.location,
                domainGitHubUserDetail.login,
                domainGitHubUserDetail.name,
                domainGitHubUserDetail.type,
                domainGitHubUserDetail.updatedAt,
                domainGitHubUserDetail.url,
                )
    }
}