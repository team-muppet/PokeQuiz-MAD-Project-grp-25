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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.R


@Composable
fun UserProfileScreen(viewModel: UserProfileViewModel = hiltViewModel()) {
    Column(
    ) {
        Image(
            painter = painterResource(id = R.drawable.auth_image_from_firestore),
            contentDescription = "Auth image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = viewModel.userProfile.username,
            onValueChange = { /* No action for username in profile view */ },
            placeholder = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            readOnly = true,
        )

        // Similarly, you can add fields for other properties of UserProfile
    }
}
