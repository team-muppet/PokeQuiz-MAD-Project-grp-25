package com.example.pokequiz.screens.user_profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.screens.PokeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val profileService: ProfileService
) : PokeQuizViewModel() {
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _isSavingPicture = MutableStateFlow(false)
    val isSavingPicture: StateFlow<Boolean> = _isSavingPicture.asStateFlow()

    init {
        viewModelScope.launch {
            profileService.currentProfile.collect { profile ->
                _userProfile.value = profile
            }
        }
    }

    fun saveLocalProfilePicture(context: Context, uri: Uri) {
        viewModelScope.launch {
            if (_isSavingPicture.value) return@launch
            _isSavingPicture.value = true
            try {
                val savedFileUri = withContext(Dispatchers.IO) {
                    val profileDir = File(context.filesDir, "profiles").apply { mkdirs() }
                    val destFile = File(profileDir, "profile_${System.currentTimeMillis()}.jpg")
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        FileOutputStream(destFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    destFile.toURI().toString()
                }
                profileService.updateProfilePicture(savedFileUri)
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error saving profile picture: ${e.message}", e)
            } finally {
                _isSavingPicture.value = false
            }
        }
    }

    fun changeProfilePicture(imageUrl: String) {
        viewModelScope.launch {
            val current = _userProfile.value ?: return@launch
            profileService.updateProfile(current.copy(profileImg = imageUrl))
        }
    }

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            val current = _userProfile.value ?: return@launch
            if (newUsername.isNotBlank()) {
                profileService.updateProfile(current.copy(username = newUsername.trim()))
            }
        }
    }

    fun resetStats() {
        viewModelScope.launch {
            profileService.resetStats()
        }
    }
}
