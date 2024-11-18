package com.example.testgitapp.presentation.adapters

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testgitapp.R
import com.example.testgitapp.presentation.models.UserUiModel

class UserViewHolder(itemView: View, private val onItemClick : (name: String) -> Unit): RecyclerView.ViewHolder(itemView) {

    fun bind(user: UserUiModel) {
        itemView.findViewById<TextView>(R.id.name_textView).text = user.name

        Glide.with(itemView)
            .load(user.avatarUrl)
            .placeholder(R.drawable.baseline_crop_square_24)
            .error(R.drawable.baseline_error_24)
            .override(200, 200)
            .centerCrop()
            .into(itemView.findViewById(R.id.user_imageView))

        itemView.findViewById<ConstraintLayout>(R.id.user_item_root).setOnClickListener {
            onItemClick.invoke(user.name)
        }
    }
}
