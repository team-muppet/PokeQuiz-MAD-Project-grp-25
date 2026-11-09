package com.example.pokequiz.ui.components.userProfileItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokequiz.R
import com.example.pokequiz.model.UserProfile

@Composable
fun UserProfileItem(rank: Int, userProfile: UserProfile, score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFCB05), // Classic Pokémon Yellow
            contentColor = Color.Black
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            // Profile image with fallback
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userProfile.profileImg.ifEmpty { R.drawable.pokequiz })
                    .fallback(R.drawable.pokequiz)
                    .error(R.drawable.pokequiz)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            // Display username and score
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2A75BB)) // Classic Pokémon Blue
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "#$rank  ${userProfile.username}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(Color(0xFF3C5AA6))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Accuracy: $score% (${userProfile.gamesPlayed} games, ${userProfile.totalGuesses} guesses)",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
