package com.example.pokequiz.screens.pokemon_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.AppState
import com.example.pokequiz.ui.components.pokemonDetails.ImageCarousel
import com.example.pokequiz.ui.components.pokemonDetails.PokemonEvolutions
import com.example.pokequiz.ui.components.pokemonDetails.PokemonInfoRow

@Composable
fun PokemonDetails(
    appState: AppState,
    pokemonId: String,
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val pokemonDetails by viewModel.currentDetails.observeAsState()
    val pokemonList by viewModel.pokemonList.observeAsState()

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetails(pokemonId.toInt())
    }

    pokemonDetails?.let { details ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = details.name.replaceFirstChar { it.uppercase() },
                    fontSize = 48.sp,
                    modifier = Modifier.padding(8.dp) // Padding to individual items
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(12.dp, 0.dp)
                ) {
                    details.types.forEach { type ->
                        val assetPath = "file:///android_asset/types/$type.svg"
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            // Icon
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(assetPath)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                contentDescription = "$type type",
                                modifier = Modifier.size(24.dp)
                            )

                            // Name
                            Text(
                                text = type.replaceFirstChar { it.uppercase() },
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }

            item {
                ImageCarousel(spriteUrls = details.sprites)
            }

            item {
                PokemonInfoRow(details)
            }

            if (!pokemonList.isNullOrEmpty()) {
                item {
                    PokemonEvolutions(appState, details, pokemonList!!)
                }
            }
        }
    } ?: run {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}
