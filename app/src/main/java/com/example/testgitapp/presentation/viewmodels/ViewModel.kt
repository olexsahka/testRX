package com.example.testgitapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.example.testgitapp.domain.repository.GithubRepository
import com.example.testgitapp.presentation.models.GithubUsersResult
import com.example.testgitapp.presentation.models.UiMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubViewModel(
    private val githubRepository: GithubRepository,
    private val uiMapper: UiMapper
): ViewModel() {

    private val disposable = CompositeDisposable()
    val usersResult = MutableLiveData<GithubUsersResult>()

    fun loadUsers(){
        disposable.add(
            githubRepository.getPagingUsers()
                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {_ ->
                    usersResult.value = GithubUsersResult.Loading
                }
                .onErrorReturn {
                    PagingData.empty()
                }
                .subscribe({ onNext ->
                    usersResult.value = GithubUsersResult.Success(
                        onNext.map {
                            uiMapper.toUiModel(it)
                        }
                    )
                },{ onError ->
                    usersResult.value = GithubUsersResult.Error(onError)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}