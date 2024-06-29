package com.solodev.demo.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") pageCount: Int,
        @Query("skip") page: Int,
    ): ProductsResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}