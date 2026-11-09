package com.example.pokequiz.model.service

import com.example.pokequiz.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileService {
    val allUserProfiles: Flow<List<UserProfile>>
    val currentProfile: Flow<UserProfile>

    suspend fun updateProfile(profile: UserProfile)
    suspend fun updateProfilePicture(pictureUri: String)
    suspend fun resetStats()
}
