package com.example.pokequiz.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokequiz.AppState

@Composable
fun PokemonList(appState: AppState, pokemonListViewModel: PokemonListViewModel = hiltViewModel())  {
    val pokemonList = pokemonListViewModel.pokemon.observeAsState(initial = emptyList()).value

//    Button(onClick = { appState.navigate("pokemonDetails/1") }) {
//        Text(text = "Go To Details")
//    }

    Column {
        Text("Pokemon List")
        LazyColumn {
            items(pokemonList) { pokemon ->
                Text(pokemon.name)  // Assuming each pokemon object has a 'name' attribute

            }
        }
    }
}