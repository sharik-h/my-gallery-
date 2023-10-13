package com.example.myapplication.data

import com.example.myapplication.model.imagesItem

interface Repo {
    suspend fun getEntirePage(page: Int, limit: Int): List<imagesItem>
}