package com.example.testgitapp.presentation.github_user_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testgitapp.domain.model.Result
import com.example.testgitapp.domain.repository.GithubRepository
import com.example.testgitapp.domain.repository.Mediator
import com.example.testgitapp.presentation.models.GithubUserDetailsResult
import com.example.testgitapp.presentation.models.UiMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class GitHubUserDetailViewModel(
    private val mediator: Mediator,
    private val uiMapper: UiMapper
): ViewModel() {
    private val disposable = CompositeDisposable()

    private val _usersResult  = MutableLiveData<GithubUserDetailsResult>()
    val usersResult : LiveData<GithubUserDetailsResult> = _usersResult

    fun loadDetail(name: String){
        disposable.clear()
        _usersResult.postValue(GithubUserDetailsResult.Loading)
        disposable.add(
            mediator.fetchDetail(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNext ->
                    Log.d("res131231",onNext.toString())
                    if(onNext is Result.Success){
                        _usersResult.postValue(
                            GithubUserDetailsResult.Success(
                                uiMapper.toGithubUserUiDetailModel(onNext.data))
                        )
                    }
                    else {
                        _usersResult.postValue(GithubUserDetailsResult.Error(Exception("Get empty data")))
                    }

                }, { onError ->
                    Log.d("res12312312",onError.toString())

                    _usersResult.postValue(GithubUserDetailsResult.Error(onError))
                })
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}