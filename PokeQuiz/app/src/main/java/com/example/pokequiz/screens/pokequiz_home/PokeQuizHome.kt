package com.example.pokequiz.screens.pokequiz_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokequiz.AppState
import com.example.pokequiz.POKEMON_LIST
import com.example.pokequiz.POKE_QUIZ1
import com.example.pokequiz.POKE_QUIZ2
import com.example.pokequiz.POKE_QUIZ3
import com.example.pokequiz.R
import com.example.pokequiz.SCOREBOARD
import com.example.pokequiz.USER_PROFILE

@Composable
fun PokeQuizHome(appState: AppState) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokequiz),
            contentDescription = "PokeQuiz Icon",
            modifier = Modifier.size(180.dp)
        )

        Text(
            text = "Welcome to PokéQuiz!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Test your Pokémon mastery with quizzes or explore the Pokédex!",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quiz Mode Buttons
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🎯 Silhouette Quiz",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Identify Pokémon purely by their black silhouette.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Button(
                    onClick = { appState.navigate(POKE_QUIZ3) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Play Silhouette Quiz")
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🃏 Card Quiz",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Identify the Pokémon card as it slowly unblurs with each guess.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Button(
                    onClick = { appState.navigate(POKE_QUIZ2) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Play Card Quiz")
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "🔍 Details Quiz",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Guess the secret Pokémon matching stats like types, height, and habitat.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Button(
                    onClick = { appState.navigate(POKE_QUIZ1) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Play Details Quiz")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { appState.navigate(POKEMON_LIST) },
                modifier = Modifier.weight(1f)
            ) {
                Text("📖 Pokédex")
            }
            OutlinedButton(
                onClick = { appState.navigate(SCOREBOARD) },
                modifier = Modifier.weight(1f)
            ) {
                Text("🏆 Scoreboard")
            }
            OutlinedButton(
                onClick = { appState.navigate(USER_PROFILE) },
                modifier = Modifier.weight(1f)
            ) {
                Text("👤 Profile")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
