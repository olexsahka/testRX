package com.example.testgitapp.presentation.viewmodels

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.testgitapp.domain.repository.GithubPagerRepository
import com.example.testgitapp.domain.repository.GithubRepository
import com.example.testgitapp.domain.repository.Mediator
import com.example.testgitapp.presentation.github_list_users.GithubUserListViewModel
import com.example.testgitapp.presentation.github_user_details.GitHubUserDetailViewModel
import com.example.testgitapp.presentation.models.UiMapper

object ViewModelFactory{
    fun provideListViewModel(githubPagerRepository: GithubPagerRepository, uiMapper: UiMapper,): AbstractSavedStateViewModelFactory =
        object : AbstractSavedStateViewModelFactory(){
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return GithubUserListViewModel(githubPagerRepository, uiMapper) as T
            }
        }

    fun provideDetailViewModel(mediator: Mediator, uiMapper: UiMapper,): AbstractSavedStateViewModelFactory =
        object : AbstractSavedStateViewModelFactory(){
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return GitHubUserDetailViewModel(mediator, uiMapper) as T
            }
        }

}