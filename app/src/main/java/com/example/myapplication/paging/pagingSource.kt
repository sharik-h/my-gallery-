package com.example.myapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.Api
import com.example.myapplication.model.imagesItem
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(
    private val api : Api,
) : PagingSource<Int, imagesItem>() {



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, imagesItem> {
        return try {
            val currentPage = params.key ?: 1
            val images = api.getimgs(
                page = currentPage,
                limit = 60
            )
            LoadResult.Page(
                data = images,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (images.isEmpty()) null else currentPage + 1
            )
        }catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, imagesItem>): Int? {
        return state.anchorPosition
    }

}