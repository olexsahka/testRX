package com.example.testgitapp.data.mediator

import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.model.Result
import com.example.testgitapp.domain.repository.GithubRepository
import com.example.testgitapp.domain.repository.LRUCacheRepository
import com.example.testgitapp.domain.repository.Mediator
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class MediatorImpl(
    private val lruCacheRepository: LRUCacheRepository,
    private val githubRepository: GithubRepository,
) : Mediator {
    override fun fetchDetail(name: String): Maybe<Result<DomainGitHubUserDetail>> =
        lruCacheRepository.get(name)
            .map { detail ->
                Result.Success(detail) as Result<DomainGitHubUserDetail>
            }
            .switchIfEmpty(
                githubRepository.getUsersDetail(name).doOnSuccess {
                }
                    .flatMapMaybe { detail ->
                        if (detail is Result.Success) {
                            lruCacheRepository.put(name, detail.data).andThen(Maybe.just(detail)).doOnComplete{
                            }
                        }
                        else{
                            Maybe.just(detail)
                        }
                    }
            )
            .subscribeOn(Schedulers.io())
}