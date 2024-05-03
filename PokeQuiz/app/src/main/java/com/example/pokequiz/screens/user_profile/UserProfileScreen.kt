package com.example.pokequiz.screens.user_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow


@SuppressLint("UnrememberedMutableState")
@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = hiltViewModel()) {
    val downloadUrl = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }
    val profile = viewModel.userProfile.collectAsState(emptyList())
    val username = profile.value.getOrNull(0)?.username ?: "USERNAME"
    println("username URL: $username")
    val userid = profile.value.getOrNull(0)?.profileImg ?: "userid"
    println("userid URL: $userid")
    imageUrl.value = profile.value.getOrNull(0)?.profileImg ?: "No"
    println("imageUrl URL: $imageUrl")

    val storageReference = if (imageUrl.value != "No") {
        Firebase.storage.getReferenceFromUrl(imageUrl.value)
    } else {
        Firebase.storage.getReferenceFromUrl("gs://pokequiz-1d3fc.appspot.com/public/Surprised_Pikachu_HD_FB.jpg")
    }
    // Generate the download URL for the file
    storageReference.downloadUrl.addOnSuccessListener { uri ->
        downloadUrl.value = uri.toString()
        // Use the download URL to access the file
        println("Download URL: $downloadUrl")
    }.addOnFailureListener { exception ->
        // Handle any errors that occur while generating the download URL
        println("Error generating download URL: ${exception.message}")
    }
    Column(
    ) {

        AsyncImage(
            model = downloadUrl.value,
            contentDescription = "Mpuuetman",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Adjust the height as needed)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { /* Handle value change */ },
            label = { Text("Username111111") },
            modifier = Modifier.fillMaxWidth()
        )

        // Similarly, you can add fields for other properties of UserProfile
    }
}
