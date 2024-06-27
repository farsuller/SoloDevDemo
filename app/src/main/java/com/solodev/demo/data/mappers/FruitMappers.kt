package com.solodev.demo.data.mappers

import com.solodev.demo.data.local.FruitEntity
import com.solodev.demo.data.remote.FruitDto
import com.solodev.demo.domain.model.Fruit

fun FruitDto.toFruitEntity(): FruitEntity {
    return FruitEntity(
        id = id,
        name = name,
        family = family,
        order = order,
        genus = genus
    )
}

fun FruitEntity.toFruit(): Fruit {
    return Fruit(
        id = id,
        name = name,
        family = family,
        order = order,
        genus = genus
    )
}