package com.solodev.demo.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.solodev.demo.data.local.FruitDatabase
import com.solodev.demo.data.local.FruitEntity
import com.solodev.demo.data.mappers.toFruitEntity
import com.solodev.demo.domain.repository.FruitRepository
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FruitRemoteMediator(
    private val fruitDatabase: FruitDatabase,
    private val fruitRepository: FruitRepository,
) : RemoteMediator<Int, FruitEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FruitEntity>
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
            val fruits = fruitRepository.getPaginatedFruits(
                page = loadKey,
                perPage = state.config.pageSize
            )

            println("Fruits: $fruits loadKey $loadKey pageSize ${state.config.pageSize}")

            fruitDatabase.withTransaction {
                if(loadType == LoadType.REFRESH){
                    fruitDatabase.dao.clearAll()
                    println("Fruits: clearAll")
                }
                val fruitEntities = fruits.map { it.toFruitEntity() }
                fruitDatabase.dao.upsertAll(fruitEntities)
                println("Fruits: upsertAll $fruitEntities")
            }

            MediatorResult.Success(endOfPaginationReached = fruits.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}