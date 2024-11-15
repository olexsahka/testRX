package com.example.testgitapp.data.remote.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.testgitapp.data.remote.GithubApi
import com.example.testgitapp.data.remote.GithubSource
import com.example.testgitapp.data.remote.models.DataMapper
import com.example.testgitapp.domain.model.DomainUser
import com.example.testgitapp.domain.repository.GithubRepository
import io.reactivex.rxjava3.core.Flowable

class GithubRepositoryImpl(private val api: GithubApi, private val dataMapper: DataMapper) : GithubRepository {
    override fun getPagingUsers(): Flowable<PagingData<DomainUser>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                GithubSource(api, dataMapper)
            }
        ).flowable
    }

}