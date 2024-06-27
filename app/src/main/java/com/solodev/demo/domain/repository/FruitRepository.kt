package com.solodev.demo.domain.repository

import com.solodev.demo.data.remote.FruitApi
import com.solodev.demo.data.remote.FruitDto
import kotlinx.coroutines.delay
import javax.inject.Inject

class FruitRepository @Inject constructor(private val api: FruitApi) {

    suspend fun getPaginatedFruits(page: Int, perPage: Int): List<FruitDto> {
        // Add delay to simulate loading
        delay(2000)

        val allFruits = api.getFruits()

        // Calculate indices ensuring they are within bounds
        val start = (page - 1) * perPage
        val end = minOf(start + perPage, allFruits.size)

        // Return sublist only if start is within bounds
        return if (start < allFruits.size) {
            allFruits.subList(start, end)
        } else {
            emptyList() // or handle the case where start is out of bounds appropriately
        }
    }
}