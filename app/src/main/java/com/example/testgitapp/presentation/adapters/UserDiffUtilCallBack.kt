package com.example.testgitapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.testgitapp.presentation.models.UiModel

class UserDiffUtilCallBack(): DiffUtil.ItemCallback<UiModel>() {
    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem == newItem
    }
}
