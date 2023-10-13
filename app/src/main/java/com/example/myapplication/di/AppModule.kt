package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.Api
import com.example.myapplication.data.RepoImpl
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
    fun provideRepo(api: Api, @ApplicationContext context: Context): RepoImpl {
        return RepoImpl(api, context)
    }

}