package com.example.pokequiz.screens.user_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage


@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = hiltViewModel()) {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageRef: StorageReference = storage.getReferenceFromUrl("gs://pokequiz-1d3fc.appspot.com/public/Surprised_Pikachu_HD_FB.jpg")

    val painter3 = rememberAsyncImagePainter(storageRef)

    val profile = viewModel.userProfile.collectAsState(emptyList())
    val username = profile.value.getOrNull(0)?.username ?: "USERNAME"
    val imageUrl = "gs://pokequiz-1d3fc.appspot.com/public/Surprised_Pikachu_HD_FB.jpg"
    val storageReference = Firebase.storage.getReferenceFromUrl(imageUrl)
    // Generate the download URL for the file
    var downloadUrl = ""
    storageReference.downloadUrl.addOnSuccessListener { uri ->
        downloadUrl = uri.toString()
        // Use the download URL to access the file
        println("Download URL: $downloadUrl")
    }.addOnFailureListener { exception ->
        // Handle any errors that occur while generating the download URL
        println("Error generating download URL: ${exception.message}")
    }
    val painter = rememberAsyncImagePainter(downloadUrl)
    var fuckme = rememberAsyncImagePainter(model = downloadUrl)
    val painter2 = rememberAsyncImagePainter(model = "https://firebasestorage.googleapis.com/v0/b/pokequiz-1d3fc.appspot.com/o/public%2FSurprised_Pikachu_HD_FB.jpg?alt=media&token=919a8fe3-82ed-4375-8289-fffd0256c6b4")
    Column(
    ) {

        Image(
            painter = painter2,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Adjust the height as needed
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
