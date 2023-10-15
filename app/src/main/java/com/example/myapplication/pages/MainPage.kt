package com.example.myapplication.pages



import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myapplication.R
import com.example.myapplication.data.ViewModel
import com.example.myapplication.navigation.Screen
import com.example.myapplication.pages.custom.ErrorMessage
import com.example.myapplication.pages.custom.ImageBox
import com.example.myapplication.pages.custom.loader
import com.example.myapplication.pages.custom.nextPageLoader
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(navController: NavController, viewModel: ViewModel) {

    var viewbyList by remember { mutableStateOf(1) }
    val viewImage = if (viewbyList != 1) painterResource(id = R.drawable.grid_view)
                    else painterResource(id = R.drawable.list_view)
    val imageFlow = viewModel.imgFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

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
                imageFlow.apply {
                    when {
                       loadState.refresh is LoadState.Loading -> {
                           item {
                               loader(modifier = Modifier.fillMaxSize())
                           }
                       }
                       loadState.refresh is LoadState.Error -> {
                           val error = imageFlow.loadState.refresh as LoadState.Error
                           item {
                               ErrorMessage(
                                   modifier = Modifier.fillMaxSize(),
                                   message = error.error.localizedMessage!!,
                                   onClickRetry = { retry() })
                           }
                       }

                       loadState.append is LoadState.Loading -> {
                           item { nextPageLoader(modifier = Modifier) }
                       }

                       loadState.append is LoadState.Error -> {
                           val error = imageFlow.loadState.append as LoadState.Error
                           item {
                               ErrorMessage(
                                   modifier = Modifier,
                                   message = error.error.localizedMessage!!,
                                   onClickRetry = { retry() })
                           }
                       }
                   }
               }
            }
        }
    }
}