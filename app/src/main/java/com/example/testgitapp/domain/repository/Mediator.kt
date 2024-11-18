package com.example.testgitapp.domain.repository

import com.example.testgitapp.domain.model.DomainGitHubUserDetail
import com.example.testgitapp.domain.model.Result
import io.reactivex.rxjava3.core.Maybe

interface Mediator {
    fun fetchDetail(name: String) : Maybe<Result<DomainGitHubUserDetail>>
}