package com.example.testgitapp.data.remote.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testgitapp.domain.model.Result
import com.example.testgitapp.domain.model.DomainUser
import com.example.testgitapp.domain.repository.GithubRepository
import io.reactivex.rxjava3.schedulers.Schedulers

class GettingUsersPagingDataSource(
    private val githubRepository: GithubRepository,
    private val query: String
) : PagingSource<Int,DomainUser>() {
    override fun getRefreshKey(state: PagingState<Int, DomainUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainUser> {
        val page = params.key ?: 1

        val response = if (query.isEmpty())
            githubRepository.getGithubUserList(page,params.loadSize)
        else
            githubRepository.searchUsersByQuery(query, page, params.loadSize)

        return try {
            val resSync = response
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingGet()

            if (resSync is Result.Success ) {
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (resSync.data.items.isEmpty()) null else page + 1

                LoadResult.Page(
                    data = resSync.data.items,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {

                LoadResult.Error(Throwable("Error With  Load Paggging Data"))
            }
        } catch (e: Exception){
            Log.d("errror",e.toString())
            LoadResult.Error(e)
        }
    }
}