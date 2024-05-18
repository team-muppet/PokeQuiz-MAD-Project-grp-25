package com.example.pokequiz.ui.components.userProfileItem

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pokequiz.model.UserProfile
import com.google.firebase.storage.FirebaseStorage

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