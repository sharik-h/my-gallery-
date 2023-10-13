package com.example.myapplication.data

import com.example.myapplication.model.imagesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("list")
    fun getEntirePage(
        @Query("page") page: String,
        @Query("limit") limit: String
    ): Call<List<imagesItem>>

}