package com.example.pokequiz.ui.components.PokemonSelector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.R
import com.example.pokequiz.model.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonSelector(
    modifier: Modifier = Modifier,
    gamePokemon: List<Pokemon>,
    showImage: Boolean = false,
    makeGuess: (Pokemon) -> Unit
){
    var active by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredPokemon = gamePokemon.filter { it.name.contains(searchQuery, true) }

    Column(
        modifier = Modifier.padding(10.dp),
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
            ,
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            active = active,
            onActiveChange = {active = it},
            placeholder = { Text("Select a pokemon...") },
            onSearch = {
                if(filteredPokemon.isNotEmpty()){
                    active = false
                    searchQuery = ""
                    makeGuess(filteredPokemon.first())
                }
            },
            leadingIcon = {
                IconButton(onClick = { active = false }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "MenuButton",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        ) {
            LazyColumn{
                items(filteredPokemon){
                    ListItem(
                        modifier = modifier
                            .clickable {
                                searchQuery = ""
                                active = false
                                makeGuess(it)
                            },
                        headlineContent = { Text(text = it.name.replaceFirstChar { it.titlecase() })},
                        leadingContent = {
                            if(showImage){
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("file:///android_asset/images/${it.id}.svg")
                                        .fallback(R.drawable.ic_launcher_foreground)
                                        .error(R.drawable.ic_launcher_foreground)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .build(),
                                    contentDescription = "",
                                    loading = { CircularProgressIndicator(modifier = Modifier.align(
                                        Alignment.Center)) },
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}