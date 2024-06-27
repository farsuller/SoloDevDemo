package com.solodev.demo.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FruitDao {

    @Upsert
    suspend fun upsertAll(fruits: List<FruitEntity>)

    @Query("SELECT * FROM FruitEntity")
    fun pagingSource(): PagingSource<Int, FruitEntity>

    @Query("DELETE FROM FruitEntity")
    suspend fun clearAll()
}