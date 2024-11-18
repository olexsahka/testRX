package com.example.testgitapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cached_table")
data class DetailEntity(
    val id : Int,
    val lastAccessed: Long,
    val avatarUrl: String?,
    val blog: String?,
    val company: String?,
    val createdAt: String?,
    val eventsUrl: String?,
    val followers: Int?,
    val following: Int?,
    val gistsUrl: String?,
    val location: String?,
    @PrimaryKey
    val login: String,
    val name: String?,
    val type: String?,
    val updatedAt: String?,
    val url: String?,
)