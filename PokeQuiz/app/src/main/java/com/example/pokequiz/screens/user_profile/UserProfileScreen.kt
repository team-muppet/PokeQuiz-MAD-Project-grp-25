package com.example.pokequiz.screens.user_profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.R
import androidx.compose.foundation.Image
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await


@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = hiltViewModel()) {
    Column(
    ) {
        val profile = viewModel.userProfile.collectAsState(emptyList())
        val username = profile.value.getOrNull(0)?.username ?: "USERNAME"
        val storageReference = Firebase.storage.getReferenceFromUrl("gs://pokequiz-1d3fc.appspot.com/Surprised_Pikachu_HD_FB.jpg")
        val imageUrl = storageReference.downloadUrl
        val painter = rememberAsyncImagePainter(imageUrl)
        Image(
            painter = painter,
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { /* Handle value change */ },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        // Similarly, you can add fields for other properties of UserProfile
    }
}
