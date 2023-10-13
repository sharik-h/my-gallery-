package com.example.myapplication.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _images = MutableLiveData<List<imagesItem>>()
    val images: LiveData<List<imagesItem>> get() = _images
    private val _imageViewing = MutableLiveData<imagesItem>()
    val imageViewing: LiveData<imagesItem> get() = _imageViewing
    private val _newImage = mutableStateOf(imagesItem("", "", "", "", "", ""))
    val newImage = _newImage

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
            updateImages(room.getAllImage())
        }
    }


    private fun isAllFeildOk(): Boolean {
        return newImage.value?.author != null &&
                newImage.value?.width != null &&
                newImage.value?.height != null &&
                newImage.value?.url != null &&
                newImage.value?.download_url != null
    }
    fun addNewImage(){
        if (isAllFeildOk()){
            viewModelScope.launch {
                room.addNewImage(_newImage.value!!)
            }
          clearNewImage()
        }
    }

    fun clearNewImage(){
        updateNewImage("author", "")
        updateNewImage("width", "")
        updateNewImage("height", "")
        updateNewImage("url", "")
        updateNewImage("download_url", "")
    }
    fun updateImages(images: List<imagesItem>){
        _images.value = images
    }

    fun deleteImage(image: imagesItem) {
        _images.value = images.value?.filter { it != image }
        viewModelScope.launch {
            room.deleteAImage(image)
        }
    }

    fun setAsViewing(image: imagesItem) {
        _imageViewing.value = image
    }

    fun updateNewImage(name: String, value: String){
        _newImage.let {
            when(name){
                "author" -> it.value = it.value.copy(author = value)
                "width" -> it.value = it.value.copy(width = value)
                "height" -> it.value = it.value.copy(height = value)
                "url" -> it.value = it.value.copy(url = value)
                "download_url"  -> it.value = it.value.copy(download_url = value)
            }
        }
    }
}