package com.example.pokequiz.screens.scoreboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokequiz.HOME
import com.example.pokequiz.ui.components.userProfileItem.UserProfileItem

@Composable
fun Scoreboard(viewModel: ScoreboardViewModel = hiltViewModel(), navController: NavHostController) {
    val allProfiles by viewModel.allProfiles.collectAsState()
    val listState = rememberLazyListState()

    BackHandler {
        navController.navigate(HOME) {
            popUpTo(HOME) { inclusive = true }
            launchSingleTop = true
        }
    }

    if (allProfiles.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No scoreboard records yet. Play a quiz to see your rank!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 8.dp)) {
            Text(
                text = "Trainer Hall of Fame",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )
            LazyColumn(state = listState) {
                itemsIndexed(allProfiles) { index, userProfile ->
                    val score = viewModel.calculateScore(userProfile)
                    UserProfileItem(rank = index + 1, userProfile = userProfile, score = score)
                }
            }
        }
    }
}
