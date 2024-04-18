package com.example.pokequiz.ui.components

import androidx.lifecycle.ViewModel
import com.example.pokequiz.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavDrawerViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    suspend fun signOut() {
        accountService.signOut()
    }
}