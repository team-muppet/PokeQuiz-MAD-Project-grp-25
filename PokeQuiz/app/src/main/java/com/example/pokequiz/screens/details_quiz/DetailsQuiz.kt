package com.example.pokequiz.screens.details_quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokequiz.ui.components.PokemonSelector.PokemonSelector
import com.example.pokequiz.ui.components.detailsGuessList.DetailsGuessList

@Composable
fun DetailsQuiz(viewModel: DetailsQuizViewModel = hiltViewModel(), navController: NavHostController){
    val currentDetails by viewModel.currentDetails.observeAsState()
    val guessedPokemon by viewModel.guessedPokemon.observeAsState()
    val gamePokemon by viewModel.gamePokemon.observeAsState()
    val gameState by viewModel.gameState.observeAsState()

    // Handle back button press
    BackHandler {
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
            launchSingleTop = true
        }
    }
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            currentDetails == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                // Play again button
                if(gameState == true){
                    Text(text = "You win!", modifier = Modifier.padding(top = 16.dp))
                    Button(onClick = { viewModel.resetGame() }) {
                        Text(text = "Play again")
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    PokemonSelector(
                        gamePokemon = gamePokemon.orEmpty(),
                        makeGuess = { pokemon -> viewModel.checkGuess(pokemon) },
                        modifier = Modifier.zIndex(1f),
                        showImage = true
                    )

                    Box(modifier = Modifier.padding(top = 75.dp)) {
                        DetailsGuessList(guessedPokemon = guessedPokemon ?: emptyList(), current = currentDetails!!)
                    }
                }
            }
        }
    }
}