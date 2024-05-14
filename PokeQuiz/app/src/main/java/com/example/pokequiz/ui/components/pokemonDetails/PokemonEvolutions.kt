package com.example.pokequiz.ui.components.pokemonDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.AppState
import com.example.pokequiz.POKEMON_DETAILS
import com.example.pokequiz.R
import com.example.pokequiz.model.PokeminDetails
import com.example.pokequiz.model.Pokemon

@Composable
fun PokemonEvolutions(appState: AppState, detailed: PokeminDetails, pokemonList: List<Pokemon>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(detailed.evolutionChain) { evolution ->
            val pokemon = pokemonList.find { it.id == evolution.id }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(enabled = true) {
                    appState.navigate("$POKEMON_DETAILS/${evolution.id}")
                },
            ) {
                if (pokemon != null) {
                    val fileName = pokemon.img?.substringAfterLast("/")?.replace("png", "webp")
                    val assetPath = "file:///android_asset/images/$fileName"
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(assetPath)
                            .fallback(R.drawable.pokequiz)
                            .error(R.drawable.pokequiz)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = "Image of ${pokemon.name}",
                        modifier = Modifier.size(120.dp),
                    )
                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                        fontWeight = if (pokemon.id == detailed.id) FontWeight.Bold else FontWeight.Normal
                    )
                } else {
                    Text(text = evolution.name.replaceFirstChar { it.uppercaseChar() })
                }
            }
            if (evolution != detailed.evolutionChain.last()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = "Arrow Right",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
