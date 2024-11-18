package com.example.testgitapp.data.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.testgitapp.domain.model.DomainUser
import com.example.testgitapp.domain.repository.GithubRepository
import com.example.testgitapp.domain.repository.GithubPagerRepository
import io.reactivex.rxjava3.core.Flowable

class GettingUserPagingDataSourceImpl(private val githubRepository: GithubRepository):
    GithubPagerRepository {

    private companion object {
        const val LOAD_SIZE = 20
    }

    override fun getGitHubUsers(query: String): Flowable<PagingData<DomainUser>> {
        return Pager(PagingConfig(LOAD_SIZE)) {
            GettingUsersPagingDataSource(githubRepository, query)
        }.flowable
    }
}