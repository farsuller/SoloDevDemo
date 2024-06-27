package com.solodev.demo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solodev.demo.domain.model.Nutrition

@Entity
data class FruitEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val family:String?,
    val order:String?,
    val genus:String?,
)

