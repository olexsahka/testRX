package com.example.testgitapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testgitapp.presentation.models.UserUiModel
import androidx.paging.PagingDataAdapter
import com.example.testgitapp.R

class GithubUserAdapter(private val onItemClick : (name: String) -> Unit): PagingDataAdapter<UserUiModel,UserViewHolder>(UserDiffUtilCallBack()) {
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return UserViewHolder(view){ name ->
            onItemClick(name)
        }
    }

}