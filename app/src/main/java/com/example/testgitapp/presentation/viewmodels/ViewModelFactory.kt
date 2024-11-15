package com.example.testgitapp.presentation.viewmodels

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.testgitapp.data.remote.GithubApi
import com.example.testgitapp.domain.repository.GithubRepository
import com.example.testgitapp.presentation.models.UiMapper

object ViewModelFactory{
    fun provideViewModel(repository: GithubRepository, uiMapper: UiMapper): AbstractSavedStateViewModelFactory =
        object : AbstractSavedStateViewModelFactory(){
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return GithubViewModel(repository, uiMapper) as T
            }
        }

}