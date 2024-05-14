package com.example.pokequiz.ui.components.pokemonDetails

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokequiz.R
import com.example.pokequiz.model.PokeminDetails
import java.io.IOException

@Composable
fun PokemonInfoRow(detailed: PokeminDetails) {
    var isPlaying by remember { mutableStateOf(false) }
    val mediaPlayer = remember {
        MediaPlayer().apply {
            setOnCompletionListener {
                isPlaying = false
            }
        }
    }

    // Prepare the MediaPlayer asynchronously and handle exceptions
    LaunchedEffect(detailed.cry) {
        try {
            // Reset the MediaPlayer to its uninitialized state
            mediaPlayer.reset()
            mediaPlayer.setDataSource(detailed.cry)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                // MediaPlayer is ready to play
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(text = "Weight", color = Color.Gray)
            Text(text = "${detailed.weight} kg", style = MaterialTheme.typography.bodySmall)
        }

        // Play button
        Button(onClick = {
            if (isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
            isPlaying = !isPlaying
        }) {
            if (isPlaying){
                Icon(
                    painter = painterResource(R.drawable.baseline_pause_24),
                    contentDescription = "Pause"
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play"
                )
            }
        }

        Column {
            Text(text = "Height", color = Color.Gray)
            Text(text = "${detailed.height} cm", style = MaterialTheme.typography.bodySmall)
        }
    }

    // Clean up the MediaPlayer when this Composable is removed from the composition
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
