package com.example.testgitapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.testgitapp.presentation.models.UserUiModel

class UserDiffUtilCallBack(): DiffUtil.ItemCallback<UserUiModel>() {
    override fun areItemsTheSame(oldItem: UserUiModel, newItem: UserUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserUiModel, newItem: UserUiModel): Boolean {
        return oldItem == newItem
    }
}
