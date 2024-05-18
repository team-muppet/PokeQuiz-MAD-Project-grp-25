package com.example.pokequiz.screens.scoreboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokequiz.ui.components.userProfileItem.UserProfileItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Scoreboard(viewModel: ScoreboardViewModel = hiltViewModel(), navController: NavHostController) {
    val allProfiles by viewModel.allProfiles.collectAsState()

    val listState = rememberLazyListState()

    BackHandler {
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
            launchSingleTop = true
        }
    }

    LazyColumn(state = listState) {
        itemsIndexed(allProfiles) { index, userProfile ->
            val score = viewModel.calculateScore(userProfile)
            UserProfileItem(rank = index + 1, userProfile = userProfile, score = score)
        }
    }
}
