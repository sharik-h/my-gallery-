package com.example.myapplication.pages



import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.ViewModel
import com.example.myapplication.model.imagesItem
import com.example.myapplication.navigation.Screen
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(navController: NavController, viewModel: ViewModel) {

    var viewbyList by remember { mutableStateOf(1) }
    val viewImage = if (viewbyList != 1) painterResource(id = R.drawable.grid_view)
                    else painterResource(id = R.drawable.list_view)
    val imageFlow = viewModel.imgFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = { 
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_title),
                        style = MaterialTheme.typography.titleLarge
                    ) },
                actions = {
                    IconButton(onClick = { viewbyList = if(viewbyList<3)viewbyList + 1 else  1 } ) {
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

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(viewbyList),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                items(imageFlow.itemCount) {
                    if (viewbyList == 1) {

                        // this part contains function for swiping to delete the item
                        val delete = SwipeAction(
                            onSwipe = {
                                viewModel.deleteImage(imageFlow[it]!!) },
                            icon = {
                                Icon(
                                    modifier = Modifier.padding(16.dp),
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = " ",
                                    tint = Color.White) },
                            background = Color.Red
                        )
                        SwipeableActionsBox(
                            endActions = listOf(delete),
                            swipeThreshold = 120.dp,
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                            backgroundUntilSwipeThreshold = Color.LightGray
                        ) {
                            ImageBox(
                                image = imageFlow[it]!!,
                                onLongPress = {},
                                onClick = {
                                    viewModel.setAsViewing(imageFlow[it]!!)
                                    navController.navigate(Screen.detailPage.route) }
                            )
                        }
                    }else{
                        // this part contains longpress to delete
                        var isDeleteViewable by remember { mutableStateOf(false) }

                        Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
                            ImageBox(
                                image = imageFlow[it]!!,
                                onClick = {
                                    if (!isDeleteViewable){
                                        viewModel.setAsViewing(imageFlow[it]!!)
                                        navController.navigate(Screen.detailPage.route)
                                    } },
                                onLongPress = {
                                    isDeleteViewable = true
                                }
                            )
                            if (isDeleteViewable){
                                Box(
                                    modifier = Modifier
                                        .height(150.dp)
                                        .fillMaxWidth()
                                        .background(Color(0x99C2C2C2)),
                                    contentAlignment = Alignment.Center,)  {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceEvenly) {

                                        FloatingActionButton(onClick = {
                                            isDeleteViewable = false
                                            viewModel.deleteImage(imageFlow[it]!!) },
                                            shape = RoundedCornerShape(50)
                                        ) {
                                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                                        }
                                        FloatingActionButton(
                                            onClick = { isDeleteViewable = false },
                                            shape = RoundedCornerShape(50)
                                        ) {
                                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


// UI that holds the image
@Composable
fun ImageBox(
    image: imagesItem,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress() },
                    onTap = { onClick() }
                )
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        )
    ) {
        val painter = rememberAsyncImagePainter(image.download_url)
        val transition by animateFloatAsState(
            targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .alpha(transition)
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "By " + image.author,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 5.dp),
                    color = Color.White )
            }
        }
    }
}