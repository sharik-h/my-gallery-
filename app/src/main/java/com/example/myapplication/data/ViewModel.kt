package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Room.RoomRepo
import com.example.myapplication.model.imagesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repoImpl: RepoImpl,
    private val room: RoomRepo): ViewModel() {

    var images = mutableListOf<imagesItem>()

    init {
        getEntirePage(page = 1, limit = 30)
    }

    private fun getEntirePage(page: Int, limit: Int) {
        viewModelScope.launch {
            val imagesFromServer = repoImpl.getEntirePage(page, limit)

            // checks and add new item only if it doesnt exist in the database
            for(image in imagesFromServer){
                val alreadyExist = room.getImageById(image.id)
                if (alreadyExist == null) {
                    room.addNewImage(image)
                }
            }
            room.getAllImage().forEach { println(it.download_url) }
        }
    }

}