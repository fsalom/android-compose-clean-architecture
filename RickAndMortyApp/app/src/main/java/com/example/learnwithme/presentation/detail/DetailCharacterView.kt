package com.example.learnwithme.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.learnwithme.presentation.components.CustomProgressIndicator

@Composable
fun DetailCharactersView(
    viewModel: DetailCharactersViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        CustomProgressIndicator()
        viewModel.load()
    }

    uiState.character?.let {
        Box {
            AsyncImage(
                model = it.image,
                contentDescription = it.name + "image",
                modifier = Modifier
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )
            Box {
                Column(modifier = Modifier.padding(10.dp)) {
                    Spacer(modifier = Modifier.weight(1.0f))
                    Box(modifier = Modifier.padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)) {
                        Column(Modifier.padding(10.dp)) {
                            Text(
                                it.name,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    color = Color.Black
                                )
                            )
                            Text(
                                it.status,
                                style = TextStyle(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}