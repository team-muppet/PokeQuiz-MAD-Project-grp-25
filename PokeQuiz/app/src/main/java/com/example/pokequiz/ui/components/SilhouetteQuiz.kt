package com.example.pokequiz.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.pokequiz.R

data class Pokemon(val name: String, val imageId: Int)

// Sample data
val pokemonList = listOf(
    Pokemon("Pikachu", R.drawable.pikachu),
    Pokemon("Bulbasaur", R.drawable.bulbasaur),
    Pokemon("Charmander", R.drawable.charmander),
    Pokemon("Squirtle", R.drawable.squirtle)
)

@Composable
fun SilhouetteQuiz(){
    var currentPokemon by remember { mutableStateOf(pokemonList.random()) }
    var gameState by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = currentPokemon.imageId),
            contentDescription = currentPokemon.name,
            modifier = Modifier.size(300.dp),
            colorFilter = if(gameState == null) ColorFilter.tint(Color.Black) else null
        )
        //Spacer(modifier = Modifier.height(5.dp))

        pokemonList.shuffled().take(4).forEach { pokemon ->
            Button(
                onClick = {
                    gameState = if (pokemon.name == currentPokemon.name) "You win!" else "You lose!"
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
                currentPokemon = pokemonList.random()  // Change the Pokémon after a guess
                gameState = null
            }) {
                Text(text = "Play again")
            }
        }
    }
}