package com.example.pokequiz.screens.details_quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.R
import com.example.pokequiz.model.EvolutionChain
import com.example.pokequiz.model.PokemonSpecies
import com.example.pokequiz.ui.components.PokemonSelector.PokemonSelector
import com.example.pokequiz.ui.components.detailsGuessList.DetailsGuessList
import com.example.pokequiz.ui.components.guessList.GuessList

@Composable
fun DetailsQuiz(viewModel: DetailsQuizViewModel = hiltViewModel()){
    val currentDetails by viewModel.currentDetails.observeAsState()
    val guessedPokemon by viewModel.guessedPokemon.observeAsState()
    val gamePokemon by viewModel.gamePokemon.observeAsState()
    val gameState by viewModel.gameState.observeAsState()

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