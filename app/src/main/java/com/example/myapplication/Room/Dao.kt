package com.example.myapplication.Room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.imagesItem

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllImg(images: List<imagesItem>)

    @Query("DELETE FROM imagesItem")
    suspend fun clearAll()

    @Query("SELECT * FROM imagesItem")
    fun getAllImgs(): PagingSource<Int, imagesItem>

    @Insert
    suspend fun addNewImage(image: imagesItem)

    @Delete
    suspend fun deleteAImage(image: imagesItem)
}