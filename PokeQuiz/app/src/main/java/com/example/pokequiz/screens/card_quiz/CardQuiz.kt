package com.example.pokequiz.screens.card_quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.R
import com.example.pokequiz.ui.components.PokemonSelector.PokemonSelector
import com.example.pokequiz.ui.components.guessList.GuessList

@Composable
fun CardQuiz(viewModel: CardQuizViewModel = hiltViewModel()){
    val currentCard by viewModel.currentCard.observeAsState()
    val currentPokemon by viewModel.currentPokemon.observeAsState()
    val guessedPokemon by viewModel.guessedPokemon.observeAsState()
    val gamePokemon by viewModel.gamePokemon.observeAsState()
    val gameState by viewModel.gameState.observeAsState()
    val cardBlur by viewModel.cardBlur.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState()

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        when{
            currentCard == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                Text(text = "Generation 1", modifier = Modifier.align(Alignment.Start))

                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    contentAlignment = Alignment.TopCenter
                ){
                    PokemonSelector(
                        gamePokemon = gamePokemon.orEmpty(),
                        makeGuess = { pokemon -> viewModel.checkGuess(pokemon) },
                        showImage = true,
                        modifier = Modifier.zIndex(1f)
                    )

                    Box(modifier = Modifier.padding(top = 85.dp)) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(currentCard?.images?.small)
                                .fallback(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_foreground)
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            contentDescription = "Guess that pokemon!",
                            modifier = Modifier
                                .size(300.dp)
                                .blur(radiusX = cardBlur ?: 20.dp, radiusY = cardBlur ?: 20.dp)
                        ){
                            val state = painter.state
                            if(state is AsyncImagePainter.State.Loading || isLoading == true){
                                CircularProgressIndicator()
                            }
                            else{
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                // Play again button
                if(gameState == true){
                    Text(text = "You win!", modifier = Modifier.padding(top = 16.dp))
                    Button(onClick = { viewModel.resetGame() }) {
                        Text(text = "Play again")
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                // Wrong guesses
                GuessList(guessedPokemon = guessedPokemon, currentPokemon = currentPokemon)
            }
        }
    }
}