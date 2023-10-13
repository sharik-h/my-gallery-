package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun detailViewPage(navController: NavController, viewModel: ViewModel) {

    val image= viewModel.imageViewing.value!!

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = { 
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
            
            Image(painter = rememberAsyncImagePainter(model = image.download_url), contentDescription = null)
            Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                Text(text = "By ${image.author}")
                Text(text = "height & width : ${image.height}x${image.width}")
                Text(text = "source : ${image.url}")
            }
        }
    }
}