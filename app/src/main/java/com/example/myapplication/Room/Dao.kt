package com.example.myapplication.Room

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

    @Insert
    suspend fun addNewImage(image: imagesItem)

    @Query("SELECT * FROM imagesItem ORDER BY id ASC")
    suspend fun getAllImage(): List<imagesItem>

    @Delete
    suspend fun deleteAImage(image: imagesItem)

    @Query("SELECT * FROM imagesItem WHERE id = :itemId")
    suspend fun getImageById(itemId: String): imagesItem?

}