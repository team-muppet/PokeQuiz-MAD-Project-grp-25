package com.example.pokequiz.model.service

import com.example.pokequiz.model.User
import com.example.pokequiz.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileService {
    val userProfile: Flow<List<UserProfile?>>

    val allUserProfiles: Flow<List<UserProfile>>
    suspend fun createProfile(userprofile: UserProfile)
    suspend fun readProfile(profileId: String): UserProfile?
    suspend fun updateProfile(profile: UserProfile)
    suspend fun deleteProfile(profileId: String)

    suspend fun uploadProfilePicture(profilePicture: String)

    suspend fun addProfilePicture(profilePicture: String)

    suspend fun changeProfilePicture(profilePicture: String)

    suspend fun showProfilePictures()


}