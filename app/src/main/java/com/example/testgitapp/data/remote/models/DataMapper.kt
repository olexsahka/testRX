package com.example.testgitapp.data.remote.models

import com.example.testgitapp.domain.model.DomainUser

interface DataMapper{
    fun toDomainUser(githubUser: GithubUser): DomainUser

    class BaseDataMapper : DataMapper{
        override fun toDomainUser(githubUser: GithubUser): DomainUser = DomainUser(githubUser.id, githubUser.login, githubUser.email?:"empty Email", githubUser.avatarUrl)
    }
}