package com.example.myapplication.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.Room.myDatabase
import com.example.myapplication.data.Api
import com.example.myapplication.model.imagesItem
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val room: myDatabase,
    private val api: Api
): RemoteMediator<Int, imagesItem>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, imagesItem>
    ): MediatorResult {

        return  try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null){
                        1
                    }else {
                        ((lastItem.id.toInt()+1) / 20) + 1
                    }
                }
            }

            val imgs = api.getimgs(loadKey, 20)
            room.withTransaction {
                if (loadType == LoadType.REFRESH){
                    room.dao.clearAll()
                }
                room.dao.addAllImg(imgs)
            }
            MediatorResult.Success(
                endOfPaginationReached = imgs.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}