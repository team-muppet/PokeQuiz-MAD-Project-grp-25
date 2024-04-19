package com.example.pokequiz.screens.silhoutte_quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.R

@Composable
fun SilhouetteQuiz(viewModel: SilhouetteQuizViewModel = hiltViewModel()){
    val currentPool by viewModel.currentPool.observeAsState()
    val currentPokemon by viewModel.currentPokemon.observeAsState()
    val gameState by viewModel.gameState.observeAsState()

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            currentPool.isNullOrEmpty() -> CircularProgressIndicator()
            else -> {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("file:///android_asset/images/${currentPokemon?.id}.svg")
                        .fallback(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = "Guess that pokemon!",
                    modifier = Modifier.size(300.dp),
                    loading = { CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) },
                    colorFilter = if (gameState == null) ColorFilter.tint(Color.Black) else null
                )

                currentPool?.forEach { pokemon ->
                    Button(
                        onClick = { viewModel.checkGuess(pokemon.name) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = pokemon.name.replaceFirstChar { it.titlecase() })
                    }
                }

                gameState?.let {
                    Text(text = it, modifier = Modifier.padding(top = 16.dp))
                    Button(onClick = { viewModel.resetGame() }) {
                        Text(text = "Play again")
                    }
                }
            }
        }
    }
}