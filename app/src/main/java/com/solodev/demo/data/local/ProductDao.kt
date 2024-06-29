package com.solodev.demo.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {

    @Upsert
    suspend fun upsertAll(fruits: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity")
    fun pagingSource(): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM ProductEntity")
    suspend fun clearAll()
}