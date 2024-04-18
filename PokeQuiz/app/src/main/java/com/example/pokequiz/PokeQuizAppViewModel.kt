package com.example.pokequiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokequiz.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokeQuizAppViewModel @Inject constructor(
        private val accountService: AccountService
) : ViewModel() {
    val isAuthenticated = MutableLiveData<Boolean>(false)

    fun updateAuthStatus()
    {
        isAuthenticated.value = accountService.hasUser()
    }
}