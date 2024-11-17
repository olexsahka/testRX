package com.example.testgitapp.presentation.github_list_users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.cachedIn
import com.example.testgitapp.domain.model.DomainUser
import com.example.testgitapp.domain.repository.GithubPagerRepository
import com.example.testgitapp.domain.repository.GithubRepository
import com.example.testgitapp.presentation.models.GithubUsersResult
import com.example.testgitapp.presentation.models.UiMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GithubUserListViewModel(
    private val githubPagerRepository: GithubPagerRepository,
    private val uiMapper: UiMapper
): ViewModel() {

    private var currentQuery : String? = null
    private val disposable = CompositeDisposable()

    private val _usersResult  = MutableLiveData<GithubUsersResult>()
    val usersResult : LiveData<GithubUsersResult> = _usersResult



    private fun getUserList(query: String): Flowable<PagingData<DomainUser>> {
        return githubPagerRepository.getGitHubUsers(query = query)
    }

    init {
        updateQuery("")
    }

    fun updateQuery(query: String){
        if (query != currentQuery) {
            disposable.clear()
            _usersResult.value = GithubUsersResult.Success(PagingData.empty())
            currentQuery = query
            disposable.add(
                getUserList(query)
                    .debounce(100L, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onNext ->
                        _usersResult.postValue(GithubUsersResult.Success(
                            onNext.map {
                                uiMapper.toGithubUserUiModel(it)
                            }
                        ))
                    }, { onError ->
                        _usersResult.postValue(GithubUsersResult.Error(onError))
                    })

            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}