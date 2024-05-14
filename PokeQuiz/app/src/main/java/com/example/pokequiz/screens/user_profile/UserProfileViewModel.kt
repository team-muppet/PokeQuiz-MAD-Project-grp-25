package com.example.pokequiz.screens.user_profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.screens.PokeQuizViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val profileService: ProfileService
) : PokeQuizViewModel() {
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _isUploadingProfilePicture = MutableStateFlow(false)
    val isUploadingProfilePicture: StateFlow<Boolean> = _isUploadingProfilePicture.asStateFlow()

    init {
        viewModelScope.launch {
            profileService.currentProfile.collect { profile ->
                _userProfile.value = profile
            }
        }
    }

    fun isReadStoragePermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionAndPickPhoto(
        context: Context,
        requestPermissionLauncher: () -> Unit,
        launcher: () -> Unit
    ) {
        if (isReadStoragePermissionGranted(context)) {
            launcher()
        } else {
            requestPermissionLauncher()
        }
    }

    fun uploadProfilePicture(uri: Uri) {
        viewModelScope.launch {
            if (!_isUploadingProfilePicture.value) {
                _isUploadingProfilePicture.value = true
                try {
                    val storage = FirebaseStorage.getInstance()
                    val storageRef = storage.reference
                    val profilePicsRef = storageRef.child("public/${UUID.randomUUID()}.jpg")
                    profilePicsRef.putFile(uri).await()

                    val downloadUri = profilePicsRef.downloadUrl.await().toString()
                    val updatedProfile = _userProfile.value?.copy(
                        profileImg = profilePicsRef.toString(),
                        oldPictures = _userProfile.value?.oldPictures.orEmpty() + profilePicsRef.toString()
                    )
                    if (updatedProfile != null) {
                        profileService.updateProfile(updatedProfile)
                        _userProfile.value = updatedProfile
                    }
                } catch (e: Exception) {
                    Log.e("UserProfileViewModel", "Error uploading profile picture: ${e.message}")
                } finally {
                    _isUploadingProfilePicture.value = false
                }
            }
        }
    }

    fun changeProfilePicture(imageUrl: String) {
        viewModelScope.launch {
            val updatedProfile = _userProfile.value?.copy(profileImg = imageUrl)
            if (updatedProfile != null) {
                profileService.updateProfile(updatedProfile)
                _userProfile.value = updatedProfile
            }
        }
    }

    suspend fun getDownloadUrl(storageReference: StorageReference): String? {
        return try {
            storageReference.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e("UserProfileViewModel", "Error getting download URL: ${e.message}")
            null
        }
    }
}
