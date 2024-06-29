package com.solodev.demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ProductDatabase : RoomDatabase(){

    abstract val dao: ProductDao
}