package com.solodev.demo.data.remote

import retrofit2.http.GET

interface FruitApi {

    @GET("fruit/all")
    suspend fun getFruits(): List<FruitDto>

    companion object {
        const val BASE_URL = "https://www.fruityvice.com/api/"
    }
}