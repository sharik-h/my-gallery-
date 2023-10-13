package com.example.myapplication.data

import android.content.Context
import com.example.myapplication.model.imagesItem
import com.example.myapplication.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepoImpl @Inject constructor(private val api: Api, private val context: Context) : Repo {

    override suspend fun getEntirePage(page: Int, limit: Int): List<imagesItem> {
        if (NetworkUtils.isNetworkAvailable(context)){
            return withContext(Dispatchers.IO){
                api.getEntirePage(page.toString(), limit.toString()).execute().body() ?: emptyList()
            }
        }else {
            return emptyList()
        }
    }

}