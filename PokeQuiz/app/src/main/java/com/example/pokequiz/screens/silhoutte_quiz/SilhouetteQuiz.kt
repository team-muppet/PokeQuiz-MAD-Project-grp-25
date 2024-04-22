package com.example.pokequiz.screens.silhoutte_quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.R
import com.example.pokequiz.ui.components.PokemonSelector.PokemonSelector

@Composable
fun SilhouetteQuiz(viewModel: SilhouetteQuizViewModel = hiltViewModel()){
    val currentPokemon by viewModel.currentPokemon.observeAsState()
    val guessedPokemon by viewModel.guessedPokemon.observeAsState()
    val gamePokemon by viewModel.gamePokemon.observeAsState()
    val gameState by viewModel.gameState.observeAsState()

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            currentPokemon == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                // Silhouette of pokemon
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("file:///android_asset/images/${currentPokemon?.id}.svg")
                        .fallback(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = "Guess that pokemon!",
                    modifier = Modifier.size(250.dp),
                    loading = { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) },
                    colorFilter = if (gameState == null) ColorFilter.tint(Color.Black) else null
                )
                
                Spacer(modifier = Modifier.padding(5.dp))

                PokemonSelector(
                    gamePokemon = gamePokemon.orEmpty(),
                    makeGuess = { pokemon -> viewModel.checkGuess(pokemon) }
                )

                // Play again button
                gameState?.let {
                    Text(text = it, modifier = Modifier.padding(top = 16.dp))
                    Button(onClick = { viewModel.resetGame() }) {
                        Text(text = "Play again")
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                // Wrong guesses
                LazyColumn {
                    items(guessedPokemon.orEmpty().reversed()){
                        ListItem(
                            headlineContent = { Text(it.name.replaceFirstChar { it.titlecase() }) },
                            leadingContent = {
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("file:///android_asset/images/${it.id}.svg")
                                        .fallback(R.drawable.ic_launcher_foreground)
                                        .error(R.drawable.ic_launcher_foreground)
                                        .decoderFactory(SvgDecoder.Factory())
                                        .build(),
                                    contentDescription = "",
                                    loading = { CircularProgressIndicator(modifier = Modifier.align(
                                        Alignment.Center)) },
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}