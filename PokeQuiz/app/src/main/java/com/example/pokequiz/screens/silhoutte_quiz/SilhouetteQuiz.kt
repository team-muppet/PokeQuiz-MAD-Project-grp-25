package com.example.pokequiz.screens.silhoutte_quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

data class Pokemon(val name: String, val image: String)

// Sample data
val pokemonList = listOf(
    Pokemon("Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"),
    Pokemon("Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
    Pokemon("Charmander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"),
    Pokemon("Squirtle", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png")
)

@Composable
fun SilhouetteQuiz(viewModel: SilhouetteQuizViewModel = hiltViewModel()){
    val currentPokemon = remember { mutableStateOf(pokemonList.random()) }
    var gameState by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(currentPokemon.value.image)
                .crossfade(true)
                .build(),
            contentDescription = "Guess that pokemon!",
            modifier = Modifier.size(300.dp),
            colorFilter = if(gameState == null) ColorFilter.tint(Color.Black) else null
        )

        pokemonList.forEach { pokemon ->
            Button(
                onClick = {
                    gameState = if (pokemon.name == currentPokemon.value.name) "You win!" else "You lose!"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(text = pokemon.name)
            }
        }

        gameState?.let {
            Text(text = it, modifier = Modifier.padding(top = 16.dp))
            Button(onClick = {
                currentPokemon.value = pokemonList.random()  // Change the Pokémon after a guess
                gameState = null
            }) {
                Text(text = "Play again")
            }
        }
    }
}