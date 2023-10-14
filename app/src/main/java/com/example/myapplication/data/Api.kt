package com.example.myapplication.data

import com.example.myapplication.model.imagesItem
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("list")
    suspend fun getimgs(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<imagesItem>

}