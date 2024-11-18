package com.example.testgitapp.data.local.repository

import com.example.testgitapp.data.local.dao.CacheDetailDao
import com.example.testgitapp.data.local.mapper.DatabaseMapper
import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.repository.LRUCacheRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class LRUCacheRepositoryImpl(
    override val maxSize: Int = 5,
    private val detailDAo: CacheDetailDao,
    private val databaseMapper: DatabaseMapper,
) :
    LRUCacheRepository {
    override fun put(name: String, data: DomainGitHubUserDetail): Completable {
        return detailDAo.getCachedSize().flatMapCompletable { size ->
            if (size >= maxSize) {
                detailDAo.getLeastRecentlyUsedItem().flatMapCompletable { lruDetail ->
                    detailDAo.deleteItem(lruDetail.login)
                }
            } else {
                Completable.complete()
            }
        }.andThen(
            detailDAo.insertItem(
                databaseMapper.toEntity(data, System.currentTimeMillis())
            )
        ).subscribeOn(Schedulers.io())
    }


override fun get(name: String): Maybe<DomainGitHubUserDetail> {
    return detailDAo.getItem(name)
        .flatMap { item ->
            detailDAo.insertItem(
                item.copy(
                    lastAccessed = System.currentTimeMillis()
                )
            ).andThen(
                Maybe.just(
                    databaseMapper.toDomain(item)
                )
            )
        }.subscribeOn(Schedulers.io())
}

override fun clearCache(): Completable {
    return detailDAo.getLeastRecentlyUsedItem().flatMapCompletable { item ->
        detailDAo.deleteItem(item.login)
    }
}
}