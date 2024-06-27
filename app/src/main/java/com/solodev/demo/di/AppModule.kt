package com.solodev.demo.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.solodev.demo.data.local.FruitDatabase
import com.solodev.demo.data.local.FruitEntity
import com.solodev.demo.data.remote.FruitApi
import com.solodev.demo.data.remote.FruitRemoteMediator
import com.solodev.demo.domain.repository.FruitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFruitDatabase(@ApplicationContext context: Context) : FruitDatabase{
        return Room.databaseBuilder(
            context,
            FruitDatabase::class.java,
            "fruits.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideFruitApi():FruitApi{
        return Retrofit.Builder()
            .baseUrl(FruitApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFruitRepository(fruitApi: FruitApi): FruitRepository {
        return FruitRepository(api = fruitApi)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideFruitPager(fruitDb: FruitDatabase, fruitApi: FruitApi): Pager<Int, FruitEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = FruitRemoteMediator(
                fruitDatabase = fruitDb,
                fruitRepository = FruitRepository(api = fruitApi)
        ),
            pagingSourceFactory = {
                fruitDb.dao.pagingSource()
            }
        )
    }
}