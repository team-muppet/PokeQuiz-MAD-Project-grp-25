package com.example.pokequiz.screens.user_profile

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = hiltViewModel(), navController: NavHostController) {
    val isUploading by viewModel.isUploadingProfilePicture.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isDialogOpen = remember { mutableStateOf(false) }
    var currentImageUrl by remember { mutableStateOf("") }
    var currentImageReference: StorageReference? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.uploadProfilePicture(it)
        }
    }

    BackHandler {
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
            launchSingleTop = true
        }
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcher.launch("image/*")
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Please grant permission", Toast.LENGTH_LONG).show()
            }
        }

    // Update currentImageReference and fetch URL whenever userProfile changes
    LaunchedEffect(userProfile?.profileImg) {
        currentImageReference = userProfile?.profileImg?.let {
            FirebaseStorage.getInstance().getReferenceFromUrl(it)
        }
        currentImageReference?.let { reference ->
            scope.launch {
                currentImageUrl = viewModel.getDownloadUrl(reference) ?: ""
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = userProfile?.username ?: "USERNAME",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(Color.White)
                .padding(vertical = 16.dp, horizontal = 8.dp)
        )
        Box(
            modifier = Modifier
                .clickable {
                    if (!isUploading) {
                        isDialogOpen.value = true
                    }
                }
        ) {
            AsyncImage(
                model = currentImageUrl,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.requestPermissionAndPickPhoto(
                context,
                requestPermissionLauncher = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissionLauncher.launch(READ_MEDIA_IMAGES)
                    } else {
                        requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
                    }
                },
                launcher = {
                    launcher.launch("image/*")
                }
            )
        }) {
            Text("Pick Photo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Games Played: ${userProfile?.gamesPlayed ?: 0}", fontWeight = FontWeight.Bold)
                Text(text = "Total Guesses: ${userProfile?.totalGuesses ?: 0}", fontWeight = FontWeight.Bold)
            }
        }
    }

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text("Choose Profile Image")
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn {
                        items(userProfile?.oldPictures.orEmpty()) { imageUrl ->
                            var oldImageUrl by remember { mutableStateOf<String?>(null) }
                            LaunchedEffect(imageUrl) {
                                scope.launch {
                                    val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
                                    oldImageUrl = viewModel.getDownloadUrl(storageReference)
                                }
                            }
                            oldImageUrl?.let { url ->
                                AsyncImage(
                                    model = url,
                                    contentDescription = "OldProfileImage",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clickable {
                                            viewModel.changeProfilePicture(imageUrl)
                                            currentImageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
                                            scope.launch {
                                                currentImageUrl = viewModel.getDownloadUrl(currentImageReference!!) ?: ""
                                            }
                                            isDialogOpen.value = false
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
