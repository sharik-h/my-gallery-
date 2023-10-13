package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class imagesItem(
    @PrimaryKey
    val id: String,
    val author: String,
    val width: String,
    val height: String,
    val url: String,
    val download_url: String
)