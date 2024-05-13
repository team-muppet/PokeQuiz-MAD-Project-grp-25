package com.example.pokequiz.screens.scoreboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.screens.PokeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreboardViewModel @Inject constructor(
    private val accountService: AccountService,
    private val profileService: ProfileService
) : PokeQuizViewModel() {
    private val _allProfiles = MutableStateFlow<List<UserProfile>>(emptyList())
    val allProfiles: StateFlow<List<UserProfile>> = _allProfiles.asStateFlow()

    init {
        viewModelScope.launch {
            profileService.allUserProfiles.collect { profiles ->
                _allProfiles.value = profiles.sortedByDescending { calculateScore(it) }
            }
        }
    }

    fun calculateScore(userProfile: UserProfile): Int {
        return if (userProfile.totalGuesses != 0) {
            ((userProfile.gamesPlayed.toFloat() / userProfile.totalGuesses.toFloat()) * 100).toInt()
        } else {
            0
        }
    }
}
