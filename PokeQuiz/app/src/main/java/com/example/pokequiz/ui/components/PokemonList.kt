package com.example.pokequiz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.pokequiz.AppState

@Composable
fun PokemonList(appState: AppState){
    Column {
        Text("List of Pokémon here")
        Button(onClick = { appState.navigate("pokemonDetails/1") }) {
            Text(text = "Go To Details")
        }
    }
}