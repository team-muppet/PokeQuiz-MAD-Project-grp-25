package com.example.pokequiz.screens.scoreboard

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.screens.scoreboard.ScoreboardViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Scoreboard(viewModel: ScoreboardViewModel = hiltViewModel()) {
    val allProfiles by viewModel.allProfiles.collectAsState(initial = emptyList())

    val sortedProfiles = allProfiles.sortedByDescending { calculateScore(it) }

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(sortedProfiles) { userProfile ->
            UserProfileItem(userProfile = userProfile)
        }
    }
}

@Composable
fun UserProfileItem(userProfile: UserProfile, modifier: Modifier = Modifier,) {
    //val downloadUrl = remember { mutableStateOf("") }
    //val imageUrl = remember { mutableStateOf("") }
    val imageUrl = userProfile.profileImg
    println("Download URL: $imageUrl")
    val storageReference = Firebase.storage.getReferenceFromUrl(imageUrl)
    val downloadUrl = remember { mutableStateOf("") }

    storageReference.downloadUrl.addOnSuccessListener { uri ->
        downloadUrl.value = uri.toString()
        // Use the download URL to access the file
        println("Download URL: $downloadUrl")
    }.addOnFailureListener { exception ->
        // Handle any errors that occur while generating the download URL
        println("Error generating download URL: ${exception.message}")
    }

    println("Download URL: $downloadUrl")
    // Generate the download URL for the file

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Display profile image
        AsyncImage(
            model = downloadUrl.value,
            contentDescription = "Mpuuetman",
            modifier = Modifier
                 // Adjust the height as needed)
              )

        //Spacer(modifier = Modifier.width(16.dp))

        // Display username and score
        Column {
            Text(text = userProfile.username, fontWeight = FontWeight.Bold)
            Text(text = "Score: ${calculateScore(userProfile)}")
        }
    }
}

// Calculate the score based on gamesPlayed and totalGuesses
private fun calculateScore(userProfile: UserProfile): Float {
    return if (userProfile.totalGuesses != 0) {
        userProfile.gamesPlayed.toFloat() / userProfile.totalGuesses.toFloat()
    } else {
        0f
    }
}
