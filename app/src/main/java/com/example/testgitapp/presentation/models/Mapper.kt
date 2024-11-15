package com.example.testgitapp.presentation.models

import com.example.testgitapp.domain.model.DomainUser

interface UiMapper{
    fun toUiModel(domainUser: DomainUser): UiModel

    class BaseUiMapper : UiMapper{
        override fun toUiModel(domainUser: DomainUser): UiModel = UiModel(domainUser.id, domainUser.name, domainUser.email, domainUser.avatarUrl)
    }
}