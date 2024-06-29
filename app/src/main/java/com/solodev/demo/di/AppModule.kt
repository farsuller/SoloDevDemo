package com.solodev.demo.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.solodev.demo.data.local.ProductDatabase
import com.solodev.demo.data.local.ProductEntity
import com.solodev.demo.data.remote.ProductApi
import com.solodev.demo.data.remote.ProductRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "fruits.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideProductApi(): ProductApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(ProductApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }


    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideProductPager(
        productDb: ProductDatabase,
        productApi: ProductApi
    ): Pager<Int, ProductEntity> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = ProductRemoteMediator(
                productDb = productDb,
                productApi = productApi
            ),
            pagingSourceFactory = {
                productDb.dao.pagingSource()
            }
        )
    }
}