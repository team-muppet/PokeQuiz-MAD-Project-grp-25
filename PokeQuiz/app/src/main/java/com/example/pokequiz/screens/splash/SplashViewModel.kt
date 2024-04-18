package com.example.pokequiz.screens.splash


import com.example.pokequiz.HOME
import com.example.pokequiz.SIGN_IN_SCREEN
import com.example.pokequiz.SPLASH_SCREEN
import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.screens.PokeQuizViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : PokeQuizViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(HOME, SPLASH_SCREEN)
        else openAndPopUp(SIGN_IN_SCREEN, SPLASH_SCREEN)
    }
}
