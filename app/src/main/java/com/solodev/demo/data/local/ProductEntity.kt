package com.solodev.demo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val discountPercentage: Double?,
    val rating: Double?,
    val stock: Int?,
    val brand: String?,
    val weight: Int?,
    val width: Double?,
    val height: Double?,
    val depth: Double?,
    val warrantyInformation: String?,
    val availabilityStatus: String?,
    val minimumOrderQuantity: Int?
)

