package com.example.learnwithme.presentation.list
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.learnwithme.presentation.components.CustomProgressIndicator
import com.example.learnwithme.presentation.list.customview.CharacterRow
import okhttp3.internal.checkDuration

@Composable
fun ListCharactersView(viewModel: ListCharactersViewModelInterface,
                       navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        CustomProgressIndicator()
        viewModel.load()
    }



    Column {
        SearchBar(search = {
            viewModel.searchThis(it)
        })
        if (uiState.error.isNotEmpty()) {
            Toast.makeText(LocalContext.current, uiState.error, Toast.LENGTH_LONG).show()
        }
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(uiState.items.size) { index ->
                CharacterRow(uiState.items[index], navController = navController, click = {
                    viewModel.setFavorite(character = it)
                })
                var isLoading = index == uiState.items.size - 1
                if (isLoading) {
                    CustomProgressIndicator()
                    viewModel.load()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(search: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            search(text)
                        },
        label = { Text("Búsqueda") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )
}