package com.example.pokequiz.model.service.implementation

import android.content.Context
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.ProfileService
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ProfileService {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    private val defaultRivals = listOf(
        UserProfile(id = "trainer_red", username = "Red (Champion)", profileImg = "", gamesPlayed = 100, totalGuesses = 105),
        UserProfile(id = "trainer_cynthia", username = "Cynthia", profileImg = "", gamesPlayed = 85, totalGuesses = 95),
        UserProfile(id = "trainer_gary", username = "Gary Oak", profileImg = "", gamesPlayed = 60, totalGuesses = 75),
        UserProfile(id = "trainer_misty", username = "Misty", profileImg = "", gamesPlayed = 40, totalGuesses = 55),
        UserProfile(id = "trainer_brock", username = "Brock", profileImg = "", gamesPlayed = 30, totalGuesses = 50)
    )

    private val _currentProfile: MutableStateFlow<UserProfile>
    override val currentProfile: Flow<UserProfile>
        get() = _currentProfile.asStateFlow()

    private val _allUserProfiles: MutableStateFlow<List<UserProfile>>
    override val allUserProfiles: Flow<List<UserProfile>>
        get() = _allUserProfiles.asStateFlow()

    init {
        val initialProfile = loadProfile()
        _currentProfile = MutableStateFlow(initialProfile)
        _allUserProfiles = MutableStateFlow(buildLeaderboard(initialProfile))
    }

    private fun loadProfile(): UserProfile {
        val json = prefs.getString(KEY_USER_PROFILE, null)
        if (!json.isNullOrEmpty()) {
            try {
                return gson.fromJson(json, UserProfile::class.java)
            } catch (e: Exception) {
                // If parsing fails, fall back to default
            }
        }
        val defaultProfile = UserProfile(
            id = "local_player",
            username = "Ash Ketchum",
            profileImg = "",
            oldPictures = emptyList(),
            gamesPlayed = 0,
            totalGuesses = 0
        )
        saveProfile(defaultProfile)
        return defaultProfile
    }

    private fun saveProfile(profile: UserProfile) {
        val json = gson.toJson(profile)
        prefs.edit().putString(KEY_USER_PROFILE, json).apply()
    }

    private fun buildLeaderboard(userProfile: UserProfile): List<UserProfile> {
        val list = mutableListOf<UserProfile>()
        list.add(userProfile)
        list.addAll(defaultRivals)
        return list
    }

    override suspend fun updateProfile(profile: UserProfile) {
        saveProfile(profile)
        _currentProfile.value = profile
        _allUserProfiles.value = buildLeaderboard(profile)
    }

    override suspend fun updateProfilePicture(pictureUri: String) {
        val current = _currentProfile.value
        val updatedPictures = (listOf(pictureUri) + current.oldPictures).distinct()
        val updated = current.copy(
            profileImg = pictureUri,
            oldPictures = updatedPictures
        )
        updateProfile(updated)
    }

    override suspend fun resetStats() {
        val current = _currentProfile.value
        val updated = current.copy(gamesPlayed = 0, totalGuesses = 0)
        updateProfile(updated)
    }

    companion object {
        private const val PREFS_NAME = "pokequiz_local_storage"
        private const val KEY_USER_PROFILE = "key_user_profile"
    }
}
