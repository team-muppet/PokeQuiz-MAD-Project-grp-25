package com.example.pokequiz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun PokemonList(navController : NavHostController){
    Column {
        Text("List of Pokémon here")
        Button(onClick = { navController.navigate("pokemonDetails/1") }) {
            Text(text = "Go To Details")
        }
    }
}