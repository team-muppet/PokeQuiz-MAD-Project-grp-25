package com.example.pokequiz.screens.pokemon_details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PokemonDetails(pokemonId: String) {
    Text(text = "Pokémon Details $pokemonId")
}