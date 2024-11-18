package com.example.testgitapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testgitapp.data.remote.models.DataMapper
import com.example.testgitapp.domain.model.DomainUser

class GithubSource(private val githubApi: GithubApi, private val dataMapper: DataMapper) : PagingSource<Int, DomainUser>() {
    override fun getRefreshKey(state: PagingState<Int, DomainUser>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                position
            )?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainUser> {
        val page = params.key ?: 1
        return try {
            val response = githubApi.getGithubUsers(page = page, pageSize =  params.loadSize)
                .blockingGet()

            LoadResult.Page(
                data = response.items.map {
                    dataMapper.toDomainUser(it)
                },
                prevKey = if (page == 0) null else page - 1,
                nextKey = page + response.items.size
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}