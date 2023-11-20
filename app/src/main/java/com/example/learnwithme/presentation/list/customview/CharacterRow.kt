package com.example.learnwithme.presentation.list.customview

import android.widget.Space
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.learnwithme.domain.entity.Character
import com.example.learnwithme.presentation.navigation.Screen

@Composable
fun CharacterRow(character: Character, navController: NavHostController, click: (Character) -> Unit) {
    var isFavorite by remember { mutableStateOf(character.isFavorite) }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
            val id = character.id
            navController.navigate(Screen.CharacterDetail.getRouteFor(id = id))
        }) {
        Row(modifier = Modifier.padding(all = 10.dp)) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name + "image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(all = 10.dp)) {
                Text(text = character.name, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp))
                Text(text = character.species)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                isFavorite = !isFavorite
                click(character)
            }) {
                var icon = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder
                Icon(imageVector = icon, contentDescription = "favorite")
            }
        }
    }
}