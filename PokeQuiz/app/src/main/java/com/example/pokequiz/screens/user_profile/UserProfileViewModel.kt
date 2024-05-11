package com.example.pokequiz.screens.user_profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.screens.PokeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import java.util.UUID


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val profileService: ProfileService
) : PokeQuizViewModel() {
    // In a real application, you may want to load the user profile from a repository
    // For simplicity, let's create a sample user profile here
    val userProfile = profileService.userProfile

    private var currentUserProfile = profileService.currentProfile

    private var isUploadingProfilePicture = mutableStateOf(false)

    private suspend fun addProfilePicture(profilePicture: String) {
        println("CALLING ADDPROFILEAGAIN")
        if (!isUploadingProfilePicture.value) {
            isUploadingProfilePicture.value = true // Set flag to indicate upload is in progress
            val profile = currentUserProfile.first() // Collect the current profile once
            println("LOPING IN COLLECT")
            val updatedOldPictures = profile.oldPictures.toMutableList()
            updatedOldPictures.add(profilePicture)
            val updatedProfile = profile.copy(oldPictures = updatedOldPictures, profileImg = profilePicture)
            profileService.updateProfile(updatedProfile)
            isUploadingProfilePicture.value = false // Reset flag after update is completed
        }
    }


    suspend fun changeProfilePicture(profilePicture: String) {
        val profile = currentUserProfile.first() // Collect the current profile once
        println("LOPING IN COLLECT")
        val updatedProfile = profile.copy(profileImg = profilePicture)
        profileService.updateProfile(updatedProfile)

    }

    suspend fun uploadPic(picurl: String)
    {
        //profileService.uploadProfilePicture(picurl)

        uploadProfilePictureVM(picurl)
    }

    suspend fun uploadProfilePictureVM(profilePicture: String) {
        try {
            // Convert the profile picture string URI to a Uri object
            val uri = Uri.parse(profilePicture)

            // Create a storage reference from our app
            val storage = Firebase.storage
            val storageRef = storage.reference
            val refdebug = storage.toString()
            println("storageRef: $refdebug")
            // Create a reference to "profilePictures" folder and a unique filename
            val profilePicsRef = storageRef.child("public/${UUID.randomUUID()}.jpg")

            val pprefdebug = profilePicsRef.toString()
            println("profilePicsRef: $pprefdebug")

            // Upload the profile picture to Firestore Storage
            profilePicsRef.putFile(uri).await()

            addProfilePicture(pprefdebug)
            // Log success message or any further processing
            println("Profile picture uploaded successfully.")
        } catch (e: Exception) {
            // Handle any errors that occur during the upload process
            println("Error uploading profile picture: ${e.message}")
        }
    }

    fun isReadStoragePermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


}

