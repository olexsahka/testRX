package com.example.testgitapp.data.local.mapper

import com.example.testgitapp.data.local.entity.DetailEntity
import com.example.testgitapp.domain.model.DomainGitHubUserDetail

interface DatabaseMapper {

    fun toEntity(domainGitHubUserDetail: DomainGitHubUserDetail, lastAccessed: Long): DetailEntity
    fun toDomain(detailEntity: DetailEntity): DomainGitHubUserDetail

    class BaseDatabaseMapper : DatabaseMapper {
        override fun toEntity(
            domainGitHubUserDetail: DomainGitHubUserDetail,
            lastAccessed: Long
        ): DetailEntity =
            DetailEntity(
                id = domainGitHubUserDetail.id,
                lastAccessed=lastAccessed,
                avatarUrl = domainGitHubUserDetail.avatarUrl,
                blog = domainGitHubUserDetail.blog,
                company = domainGitHubUserDetail.company,
                createdAt = domainGitHubUserDetail.createdAt,
                eventsUrl = domainGitHubUserDetail.eventsUrl,
                followers = domainGitHubUserDetail.followers,
                following = domainGitHubUserDetail.following,
                gistsUrl = domainGitHubUserDetail.gistsUrl,
                location = domainGitHubUserDetail.location,
                login = domainGitHubUserDetail.login,
                name = domainGitHubUserDetail.name,
                type = domainGitHubUserDetail.type,
                updatedAt = domainGitHubUserDetail.updatedAt,
                url = domainGitHubUserDetail.url,
            )

        override fun toDomain(detailEntity: DetailEntity): DomainGitHubUserDetail =
            DomainGitHubUserDetail(
                detailEntity.avatarUrl,
                detailEntity.blog,
                detailEntity.company,
                detailEntity.createdAt,
                detailEntity.eventsUrl,
                detailEntity.followers,
                detailEntity.following,
                detailEntity.gistsUrl,
                detailEntity.id,
                detailEntity.location,
                detailEntity.login,
                detailEntity.name,
                detailEntity.type,
                detailEntity.updatedAt,
                detailEntity.url,
            )

    }
}