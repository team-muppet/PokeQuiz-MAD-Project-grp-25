package com.example.pokequiz.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.screens.user_profile.UserProfileViewModel

@Composable
fun UserProfileDialog(
    userProfile: UserProfile?,
    viewModel: UserProfileViewModel,
    isDialogOpen: MutableState<Boolean>
) {
    Dialog(onDismissRequest = { isDialogOpen.value = false }) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Choose Profile Image",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                val pictures = userProfile?.oldPictures.orEmpty()
                if (pictures.isEmpty()) {
                    Text(
                        text = "No previous pictures found. Use 'Pick Photo' to add one!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn(modifier = Modifier.height(300.dp)) {
                        items(pictures) { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Old Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        viewModel.changeProfilePicture(imageUrl)
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
