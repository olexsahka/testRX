package com.example.testgitapp.presentation.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testgitapp.R
import com.example.testgitapp.presentation.models.UiModel

class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(user: UiModel) {
        itemView.findViewById<TextView>(R.id.name_tv).text = user.name
        itemView.findViewById<TextView>(R.id.email_tv).text = user.email
    }
}
