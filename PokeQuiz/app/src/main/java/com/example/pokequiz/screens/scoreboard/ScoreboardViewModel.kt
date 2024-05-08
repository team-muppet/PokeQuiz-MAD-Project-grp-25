package com.example.pokequiz.screens.scoreboard

import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.screens.PokeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreboardViewModel @Inject constructor(
    private val accountService: AccountService,
    profileService: ProfileService
) : PokeQuizViewModel() {
    val allProfiles = profileService.allUserProfiles





}