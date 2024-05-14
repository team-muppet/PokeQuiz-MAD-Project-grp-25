package com.example.pokequiz.screens.scoreboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokequiz.model.UserProfile
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import coil.compose.AsyncImage
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.firebase.storage.FirebaseStorage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Scoreboard(viewModel: ScoreboardViewModel = hiltViewModel()) {
    val allProfiles by viewModel.allProfiles.collectAsState()

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        itemsIndexed(allProfiles) { index, userProfile ->
            val score = viewModel.calculateScore(userProfile)
            UserProfileItem(rank = index + 1, userProfile = userProfile, score = score)
        }
    }
}

@Composable
fun UserProfileItem(rank: Int, userProfile: UserProfile, score: Int, modifier: Modifier = Modifier) {
    var downloadUrl = remember { mutableStateOf("") }

    // Download the image URL from Firebase Storage
    LaunchedEffect(userProfile.profileImg) {
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(userProfile.profileImg)
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            downloadUrl.value = uri.toString()
        }.addOnFailureListener { exception ->
            Log.e("UserProfileItem", "Error generating download URL: ${exception.message}")
        }
    }

    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Yellow,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            // Display profile image with fixed size
            AsyncImage(
                model = downloadUrl.value,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 4.dp)
            )

            // Display username and score inside columns with boxes
            Column {
                Box(
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                        .fillMaxWidth()
                        .background(Color.Blue)
                        .padding(2.dp)
                ) {
                    Text(text = "$rank. ${userProfile.username}", fontWeight = FontWeight.Bold, color = Color.White)
                }
                Box(
                    modifier = Modifier
                        .background(Color.Blue)
                        .padding(2.dp)
                ) {
                    Row {
                        Text(text = "Score: ", fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "$score", color = Color.White)
                    }
                }
            }
        }
    }
}
