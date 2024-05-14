package com.example.pokequiz.screens.sign_up

import com.example.pokequiz.HOME
import com.example.pokequiz.SIGN_IN_SCREEN
import com.example.pokequiz.SIGN_UP_SCREEN
import com.example.pokequiz.model.UserProfile
import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.screens.PokeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService, private val profileService: ProfileService
) : PokeQuizViewModel() {
    val email = MutableStateFlow("")
    val username = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updateUsername(newUsername: String) {
        username.value = newUsername
    }



    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (password.value != confirmPassword.value) {
                throw Exception("Passwords do not match")
            }

            accountService.signUp(email.value, password.value)
            profileService.createProfile(UserProfile(username = username.value, profileImg = "gs://pokequiz-1d3fc.appspot.com/public/Surprised_Pikachu_HD_FB.jpg"))
            openAndPopUp(HOME, SIGN_UP_SCREEN)
        }
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SIGN_IN_SCREEN, SIGN_UP_SCREEN)
    }
}