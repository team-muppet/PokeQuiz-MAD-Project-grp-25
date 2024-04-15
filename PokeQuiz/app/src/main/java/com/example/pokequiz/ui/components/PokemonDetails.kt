package com.example.pokequiz.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PokemonDetails(pokemonId : String){
    Text(text = "Pokémon Details $pokemonId")
}