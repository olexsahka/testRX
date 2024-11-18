package com.example.testgitapp.domain.repository

import com.example.testgitapp.data.local.entity.DetailEntity
import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface LRUCacheRepository {
    val maxSize: Int

    fun put(name: String, data:DomainGitHubUserDetail) : Completable

    fun get(name: String) : Maybe<DomainGitHubUserDetail>

    fun clearCache():Completable
}