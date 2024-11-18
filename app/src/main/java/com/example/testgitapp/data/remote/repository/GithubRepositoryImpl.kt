package com.example.testgitapp.data.remote.repository

import com.example.testgitapp.data.remote.api.GithubApi
import com.example.testgitapp.data.remote.models.DataMapper
import com.example.testgitapp.domain.model.Result
import com.example.testgitapp.data.remote.responces.SearchGithubUserResponse
import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.model.DomainGithubUsers
import com.example.testgitapp.domain.repository.GithubRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class GithubRepositoryImpl(private val api: GithubApi, private val dataMapper: DataMapper) : GithubRepository {
    override fun getGithubUserList(page: Int, perPage: Int): Single<Result<DomainGithubUsers>> {
        return api.getGithubUsers(page, perPage)
            .subscribeOn(Schedulers.io())
            .map {
                it.toResult { data ->
                        dataMapper.toDomainGithubUsers(SearchGithubUserResponse(false,data,data.size),
                    )
                }
            }
            .onErrorReturn { error ->
                Result.Error(error.message.toString())
            }
    }

    override fun searchUsersByQuery(
        query: String,
        page: Int,
        perPage: Int
    ): Single<Result<DomainGithubUsers>> {
        return api.searchGithubUsers(query, page, perPage)
            .subscribeOn(Schedulers.io())
            .map {
                Result.Success(dataMapper.toDomainGithubUsers(it)) as Result<DomainGithubUsers>
            }
            .doOnError {
                Result.Error(it.message.toString())
            }
    }

    override fun getUsersDetail(name: String): Single<Result<DomainGitHubUserDetail>> {
        return api.getUserDetails(name)
            .subscribeOn(Schedulers.io())
            .map { githubUserDetailResponseResponse ->
                githubUserDetailResponseResponse.toResult {
                    dataMapper.toDomainUserDetail(it)
                }
            }
            .doOnError {
                Result.Error(it.message.toString())

            }

    }
}

inline fun <T, R> Response<T>.toResult(
    transform: (T) -> R
): Result<R> {
    return this.body()?.let { data ->
        Result.Success(transform(data))
    } ?: Result.Error("Empty data returned")
}