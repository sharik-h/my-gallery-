package com.example.myapplication.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.example.myapplication.Room.RoomRepo
import com.example.myapplication.model.imagesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val room: RoomRepo,
    private val pager: Pager<Int, imagesItem>
    ): ViewModel() {

    private val _images = MutableLiveData<List<imagesItem>>()
    val images: LiveData<List<imagesItem>> get() = _images
    private val _imageViewing = MutableLiveData<imagesItem>()
    val imageViewing: LiveData<imagesItem> get() = _imageViewing
    private val _newImage = mutableStateOf(imagesItem("", "", "", "", "", ""))
    val newImage = _newImage
    val imgFlow = pager.flow.cachedIn(viewModelScope)


    private fun isAllFeildOk(): Boolean {
        return newImage.value?.author != null &&
                newImage.value?.width != null &&
                newImage.value?.height != null &&
                newImage.value?.url != null &&
                newImage.value?.download_url != null &&
                newImage.value?.id!!.isNotEmpty()
    }
    fun addNewImage(){
        if (isAllFeildOk()){
            println(newImage)
            viewModelScope.launch {
                println("inside")
                room.addNewImage(newImage.value!!)
            }
          clearNewImage()
        }
    }

    fun clearNewImage(){
        updateNewImage("id", "")
        updateNewImage("author", "")
        updateNewImage("width", "")
        updateNewImage("height", "")
        updateNewImage("url", "")
        updateNewImage("download_url", "")
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
                "id" -> it.value = it.value.copy(id = value)
                "author" -> it.value = it.value.copy(author = value)
                "width" -> it.value = it.value.copy(width = value)
                "height" -> it.value = it.value.copy(height = value)
                "url" -> it.value = it.value.copy(url = value)
                "download_url"  -> it.value = it.value.copy(download_url = value)
            }
        }
    }
}