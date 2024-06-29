package com.solodev.demo.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.solodev.demo.data.local.ProductDatabase
import com.solodev.demo.data.local.ProductEntity
import com.solodev.demo.data.mappers.toProductEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val productDb: ProductDatabase,
    private val productApi: ProductApi,
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            delay(2000)

            val fruits = productApi.getProducts(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            println("products ${fruits.products}")

            productDb.withTransaction {
                if(loadType == LoadType.REFRESH){
                    productDb.dao.clearAll()
                }
                val productEntities = fruits.products.map { it.toProductEntity() }
                productDb.dao.upsertAll(productEntities)
            }

            MediatorResult.Success(endOfPaginationReached = fruits.products.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}