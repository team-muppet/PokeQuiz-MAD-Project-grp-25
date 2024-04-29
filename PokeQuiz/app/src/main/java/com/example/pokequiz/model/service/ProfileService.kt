package com.example.pokequiz.model.service

import com.example.pokequiz.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileService {
    val userprofile: Flow<UserProfile>
    suspend fun createProfile(userprofile: UserProfile)
    suspend fun readProfile(profileId: String): UserProfile?
    suspend fun updateProfile(profileId: String)
    suspend fun deleteProfile(profileId: String)
}