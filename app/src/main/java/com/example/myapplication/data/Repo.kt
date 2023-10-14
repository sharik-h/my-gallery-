package com.example.myapplication.data

import androidx.paging.PagingData
import com.example.myapplication.model.imagesItem
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun getimgs(): Flow<PagingData<imagesItem>>
}