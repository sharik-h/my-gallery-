package com.example.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.imagesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repoImpl: RepoImpl): ViewModel() {

    var images = mutableListOf<imagesItem>()

    init {
        getEntirePage(page = 1, limit = 30)
    }

    private fun getEntirePage(page: Int, limit: Int) {
        viewModelScope.launch {
            val h = repoImpl.getEntirePage(page, limit)
            h.forEach { println(it.download_url) }
        }
    }

}