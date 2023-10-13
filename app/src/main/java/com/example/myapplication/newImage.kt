package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newImage(navController: NavController, viewModel: ViewModel) {

    val newImage = viewModel.newImage.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add new Image") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                            viewModel.clearNewImage()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)) {

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)) {


                OutlinedTextField(
                    value = newImage.author,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Author")},
                    onValueChange = { viewModel.updateNewImage("author", it) })
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = newImage.width,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Width")},
                    onValueChange = { viewModel.updateNewImage("width", it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = newImage.height,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Height")},
                    onValueChange = { viewModel.updateNewImage("height", it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = newImage.url,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Url/Source")},
                    onValueChange = { viewModel.updateNewImage("url", it) })
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = newImage.download_url,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "Download url")},
                    onValueChange = { viewModel.updateNewImage("download_url", it) })
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        viewModel.addNewImage()
                        navController.navigateUp() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Save")
                }
            }
        }
    }

}