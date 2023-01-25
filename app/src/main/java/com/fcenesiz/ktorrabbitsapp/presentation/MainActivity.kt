package com.fcenesiz.ktorrabbitsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fcenesiz.ktorrabbitsapp.ui.theme.KtorRabbitsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorRabbitsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    ) {
                        val viewModel: MainViewModel = hiltViewModel()
                        val viewModelState = viewModel.state.collectAsState()
                        val rabbit = viewModelState.value.rabbit
                        val isLoading = viewModelState.value.isLoading

                        rabbit?.let {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(rabbit.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Rabbit"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = rabbit.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = rabbit.description)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Button(
                            onClick = viewModel::getRandomRabbits,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(text = "Next Rabbit")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        if (isLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
