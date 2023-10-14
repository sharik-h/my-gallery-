package com.example.myapplication.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.Room.myDatabase
import com.example.myapplication.data.Api
import com.example.myapplication.model.imagesItem
import com.example.myapplication.paging.ImageRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesApi(): Api {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://picsum.photos/v2/")
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): myDatabase {
        return myDatabase.getDatabase(context)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providesImgPager(room: myDatabase, api: Api): Pager<Int, imagesItem>{
        return Pager(
            config = PagingConfig(pageSize = 60),
            remoteMediator = ImageRemoteMediator(room, api),
            pagingSourceFactory = { room.dao.getAllImgs() }
        )
    }
}