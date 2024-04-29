package com.example.pokequiz.screens.user_profile

import androidx.lifecycle.ViewModel
import com.example.pokequiz.model.UserProfile

class UserProfileViewModel : ViewModel() {
    // In a real application, you may want to load the user profile from a repository
    // For simplicity, let's create a sample user profile here
    val userProfile = UserProfile(
        username = "example_user",
        profileImg = "current_profile_img_url",
        oldProfileImgs = listOf("old_profile_img_url_1", "old_profile_img_url_2", "old_profile_img_url_3"),
        game1Score = 0.0,
        game2Score = 0.0,
        game3Score = 0.0,
        userid = "user_id"
    )
}

