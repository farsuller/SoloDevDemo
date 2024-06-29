package com.solodev.demo.data.mappers

import com.solodev.demo.data.local.ProductEntity
import com.solodev.demo.data.remote.ProductDto
import com.solodev.demo.domain.model.Dimensions
import com.solodev.demo.domain.model.Product

// Mapper functions
fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        brand = brand,
        weight = weight,
        width = dimensions.width,
        height = dimensions.height,
        depth = dimensions.depth,
        warrantyInformation = warrantyInformation,
        availabilityStatus = availabilityStatus,
        minimumOrderQuantity = minimumOrderQuantity
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        tags = emptyList(),
        brand = brand,
        sku = "",
        weight = weight,
        dimensions = Dimensions(width, height, depth),
        warrantyInformation = warrantyInformation,
        shippingInformation = "",
        availabilityStatus = availabilityStatus,
        reviews = listOf(),
        returnPolicy = "",
        minimumOrderQuantity = minimumOrderQuantity,
        meta = null,
        images = emptyList(),
        thumbnail = ""
    )
}