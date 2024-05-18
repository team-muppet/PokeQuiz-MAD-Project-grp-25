package com.example.pokequiz.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.screens.user_profile.UserProfileViewModel
import kotlinx.coroutines.CoroutineScope

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

@Composable
fun UserProfileDialog(
    userProfile: UserProfile?,
    viewModel: UserProfileViewModel,
    scope: CoroutineScope,
    isDialogOpen: MutableState<Boolean>
) {
    Dialog(onDismissRequest = { isDialogOpen.value = false }) {
        Surface(modifier = Modifier.padding(16.dp)) {
            Column {
                Text("Choose Profile Image")
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(userProfile?.oldPictures.orEmpty()) { imageUrl ->
                        var oldImageUrl = remember { mutableStateOf<String?>(null) }
                        LaunchedEffect(imageUrl) {
                            scope.launch {
                                val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
                                oldImageUrl.value = viewModel.getDownloadUrl(storageReference)
                            }
                        }
                        oldImageUrl.value?.let { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = "OldProfileImage",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
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
