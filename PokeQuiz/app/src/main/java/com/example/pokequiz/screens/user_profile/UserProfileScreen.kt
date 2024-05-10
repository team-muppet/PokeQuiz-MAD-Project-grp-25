package com.example.pokequiz.screens.user_profile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

import com.google.firebase.Firebase

import com.google.firebase.storage.storage
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = hiltViewModel()) {
    var isUploading = remember { mutableStateOf(false) }
    val downloadUrl = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }
    val profile = viewModel.userProfile.collectAsState(emptyList())
    val username = profile.value.getOrNull(0)?.username ?: "USERNAME"
    println("username URL: $username")
    val userid = profile.value.getOrNull(0)?.profileImg ?: "userid"
    println("userid URL: $userid")
    imageUrl.value = profile.value.getOrNull(0)?.profileImg ?: "No"
    println("imageUrl URL: $imageUrl")

    val scope = rememberCoroutineScope()

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


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        // Handle the selected image URI here
        uri?.let {
            if (!isUploading.value) { // Check if upload is not already in progress
                isUploading.value = true // Set flag to indicate upload is in progress
                // Start a coroutine to call suspend function
                scope.launch {
                    // Call the suspend function within the coroutine context
                    viewModel.uploadPic(uri.toString())
                    val debug = uri.toString()
                    println("upload URI: $debug")
                    isUploading.value = false // Reset flag after upload is completed
                }
            }
        }
    }


    val context = LocalContext.current

    // Define a requestPermessionLauncher using the RequestPermission contract
    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        // Check if the permission is granted
        if (isGranted) {
            // Show a toast message for permission granted
// get image from android device
            launcher.launch("image/*")

            Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show()
        } else {
            // Show a toast message asking the user to grant the permission
            Toast.makeText(context, "Please grant permission", Toast.LENGTH_LONG).show()
        }
    }


    Column(
    ) {

        Box(
            modifier = Modifier
                .clickable {
                    if (!isUploading.value) {
                        // Launch image picker when not already uploading
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            launcher.launch("image/*")
                        } else {
                            launcher.launch("image/*")
                        }
                    }
                }
        ) {
            AsyncImage(
                model = downloadUrl.value,
                contentDescription = "Mpuuetman",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Adjust the height as needed)
            )
        }

        Button(onClick = { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Use the requestPermessionLauncher to request the READ_MEDIA_IMAGES permission
            requestPermissionLauncher.launch(READ_MEDIA_IMAGES)
        } else {
            // For older Android versions, use READ_EXTERNAL_STORAGE permission
            requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
        } }) {
            Text("Pick Photo")
        }

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




