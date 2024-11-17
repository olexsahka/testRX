package com.example.testgitapp.domain.repository

import androidx.paging.PagingData
import com.example.testgitapp.domain.model.DomainUser
import io.reactivex.rxjava3.core.Flowable

interface GithubRepository {
    fun getPagingUsers() : Flowable<PagingData<DomainUser>>
}