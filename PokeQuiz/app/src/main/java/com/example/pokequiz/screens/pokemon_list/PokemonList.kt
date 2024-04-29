package com.example.pokequiz.screens.pokemon_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.AppState
import com.example.pokequiz.POKEMON_DETAILS
import com.example.pokequiz.R

@Composable
fun PokemonList(appState: AppState, pokemonListViewModel: PokemonListViewModel = hiltViewModel())  {
    val pokemonList = pokemonListViewModel.pokemon.observeAsState(initial = emptyList()).value

    val listState = rememberLazyListState()

    Column {
        Text(modifier = Modifier.padding(10.dp), text = "Pokemon List:")
        LazyColumn(state = listState) {
            items(pokemonList) { pokemon ->
                ListItem(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth()
                        .clickable(enabled = true) {
                            appState.navigate("$POKEMON_DETAILS/${pokemon.id}")
                        },
                    headlineContent = { Text(pokemon.name.replaceFirstChar { it.uppercase() }, fontSize = 20.sp) },
                    overlineContent = {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            pokemon.types.forEach { type ->
                                val typeName = type.type.name
                                val assetPath = "file:///android_asset/types/$typeName.svg"
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(assetPath)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .build(),
                                    contentDescription = "${type.type.name} type",
                                    modifier = Modifier.size(24.dp) // Adjust the size as needed
                                )
                            }
                        }
                    },
                    leadingContent = {
                        val fileName = pokemon.img?.substringAfterLast("/")?.replace("png", "webp")
                        val assetPath = "file:///android_asset/images/$fileName"

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(assetPath)
                                .fallback(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_foreground)
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            contentDescription = "Image of ${pokemon.name}",
                            modifier = Modifier.size(80.dp),
                        )
                    },
                    trailingContent = { Text(text = '#' + pokemon.id.toString(), fontSize = 16.sp) }
                )
            }
        }
    }
}