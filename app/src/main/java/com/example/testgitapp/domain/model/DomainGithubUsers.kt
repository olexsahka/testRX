package com.example.testgitapp.domain.model

data class DomainGithubUsers(
    val incompleteResults: Boolean,
    val items: List<DomainUser>,
    val totalCount: Int,
)


