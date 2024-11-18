package com.example.testgitapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testgitapp.data.local.entity.DetailEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface CacheDetailDao {
    @Query("SELECT * FROM cached_table WHERE login = :login")
    fun getItem(login: String): Maybe<DetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(entity: DetailEntity): Completable

    @Query("DELETE FROM cached_table WHERE login = :login")
    fun deleteItem(login: String): Completable

    @Query("SELECT * FROM cached_table ORDER BY lastAccessed ASC LIMIT 1")
    fun getLeastRecentlyUsedItem(): Maybe<DetailEntity>

    @Query("SELECT COUNT(*) FROM cached_table")
    fun getCachedSize(): Single<Int>
}