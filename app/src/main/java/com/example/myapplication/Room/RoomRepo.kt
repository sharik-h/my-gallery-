package com.example.myapplication.Room

import androidx.paging.PagingSource
import com.example.myapplication.model.imagesItem
import javax.inject.Inject

class RoomRepo @Inject constructor(private val db: myDatabase): Dao {
    override fun addAllImg(images: List<imagesItem>) {
        db.dao.addAllImg(images)
    }

    override suspend fun clearAll() {
        db.dao.clearAll()
    }

    override fun getAllImgs(): PagingSource<Int, imagesItem> {
        return db.dao.getAllImgs()
    }

    override suspend fun addNewImage(image: imagesItem) {
        db.dao.addNewImage(image)
    }

    override suspend fun getAllImage(): List<imagesItem> {
        return db.dao.getAllImage()
    }

    override suspend fun deleteAImage(image: imagesItem) {
        return db.dao.deleteAImage(image)
    }

    override suspend fun getImageById(itemId: String): imagesItem? {
        return db.dao.getImageById(itemId)
    }
}