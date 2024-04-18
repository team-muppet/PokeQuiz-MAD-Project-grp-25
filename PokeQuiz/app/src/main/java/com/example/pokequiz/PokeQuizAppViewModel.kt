package com.example.pokequiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeQuizAppViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    private val _isAuthenticated = MutableLiveData<Boolean>(false)
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    init {
        updateAuthStatus()
    }

    private fun updateAuthStatus() {
        viewModelScope.launch {
            accountService.currentUser.collect { user ->
                _isAuthenticated.value = (user != null)
            }
        }
    }
}