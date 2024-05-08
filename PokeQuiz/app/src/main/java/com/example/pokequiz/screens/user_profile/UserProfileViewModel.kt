package com.example.pokequiz.screens.user_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.screens.PokeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val profileService: ProfileService
) : PokeQuizViewModel() {
    // In a real application, you may want to load the user profile from a repository
    // For simplicity, let's create a sample user profile here
    val userProfile = profileService.userProfile

    suspend fun uploadPic(picurl: String)
    {
        profileService.uploadProfilePicture(picurl)
    }

}

