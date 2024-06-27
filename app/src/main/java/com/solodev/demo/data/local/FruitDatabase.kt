package com.solodev.demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FruitEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FruitDatabase : RoomDatabase(){

    abstract val dao: FruitDao
}