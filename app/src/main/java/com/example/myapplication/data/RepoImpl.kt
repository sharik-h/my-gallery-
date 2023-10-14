package com.example.myapplication.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.model.imagesItem
import com.example.myapplication.paging.ImagePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RepoImpl @Inject constructor(private val api: Api) : Repo {

    override suspend fun getimgs(): Flow<PagingData<imagesItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                ImagePagingSource(api)
            }
        ).flow
    }

}