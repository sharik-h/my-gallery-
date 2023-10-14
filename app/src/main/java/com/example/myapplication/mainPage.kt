package com.example.myapplication



import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.ViewModel
import com.example.myapplication.model.imagesItem
import com.example.myapplication.navigation.Screen
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun mainPage(navController: NavController, viewModel: ViewModel) {

    val imgss: LazyPagingItems<imagesItem> = viewModel.imgs.collectAsLazyPagingItems()
    var viewbyList by remember { mutableStateOf(false) }
    var viewImage = if (viewbyList) painterResource(id = R.drawable.grid_view)
                    else painterResource(id = R.drawable.list_view)

    Scaffold(
        topBar = { 
            TopAppBar(
                title = {
                    Text(text = "My Gallery") },
                actions = {
                    IconButton(onClick = { viewbyList = !viewbyList }) {
                        Icon(painter = viewImage, contentDescription = null)
                    }
                    IconButton(onClick = { navController.navigate(Screen.newImage.route) }) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
                    }
                }
            ) 
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            if(viewbyList){
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(imgss.itemCount) { index ->

                        // swipe api used to perform delete function
                        val delete = SwipeAction(
                            onSwipe = { viewModel.deleteImage(imgss[index]!!) },
                            icon = {
                                Icon(
                                    modifier = Modifier.padding(16.dp),
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = " ",
                                    tint = Color.White
                                )
                            },
                            background = Color.Red
                        )
                        SwipeableActionsBox(
                            endActions = listOf(delete),
                            swipeThreshold = 120.dp,
                            modifier = Modifier.clip(RoundedCornerShape(25)),
                            backgroundUntilSwipeThreshold = Color.LightGray
                        ) {

                            listViewBox(image = imgss[index]!!) {
                                viewModel.setAsViewing(imgss[index]!!)
                                navController.navigate(Screen.detailPage.route)
                            }
                        }
                    }
                }
            }else{
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 10.dp,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        items(imgss.itemCount) {
                            gridViewBox(
                                image = imgss[it]!!,
                                onDelete = {viewModel.deleteImage(imgss[it]!!) },
                                onclick = {
                                    viewModel.setAsViewing(imgss[it]!!)
                                    navController.navigate(Screen.detailPage.route)
                                }
                            )
                        }
                    }
            }

        }
    }

}

// UI for the list view of the images
@Composable
fun listViewBox(
    image: imagesItem,
    onclick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        color = Color(0xFFB6B6B6),
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(25)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onclick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image.download_url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(25))
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "By\n ${image.author}")
            Spacer(modifier = Modifier.weight(0.1f))
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}



// UI for the grid view of the images
@Composable
fun gridViewBox(image: imagesItem, onDelete: () -> Unit, onclick: () -> Unit) {

    var deleteViewable by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(15),
        color = Color.LightGray,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { deleteViewable = true },
                    onTap = { onclick() }
                )
            }
    ) {
        AsyncImage(
            model = image.download_url,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Text(
            text = image.author,
            modifier = Modifier.padding(start = 6.dp, top = 3.dp),
            color = Color.White
        )
        if (deleteViewable) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f)
                    .background(Color.White),
                ) {
                    IconButton(
                        onClick = {
                            onDelete()
                            deleteViewable = false }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                Spacer(modifier = Modifier.weight(0.1f))
                    IconButton(onClick = { deleteViewable = false }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
            }
        }
    }
}