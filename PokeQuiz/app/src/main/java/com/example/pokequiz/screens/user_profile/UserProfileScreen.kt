package com.example.pokequiz.screens.user_profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokequiz.HOME
import com.example.pokequiz.R
import com.example.pokequiz.ui.components.UserProfileDialog

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val isSaving by viewModel.isSavingPicture.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val context = LocalContext.current
    val isDialogOpen = remember { mutableStateOf(false) }
    var isEditNameDialogOpen by remember { mutableStateOf(false) }
    var newNameText by remember { mutableStateOf("") }
    var isResetConfirmOpen by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.saveLocalProfilePicture(context, it)
            Toast.makeText(context, "Profile picture updated!", Toast.LENGTH_SHORT).show()
        }
    }

    BackHandler {
        navController.navigate(HOME) {
            popUpTo(HOME) { inclusive = true }
            launchSingleTop = true
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Username header with Edit button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = userProfile?.username.orEmpty().ifEmpty { "Trainer" },
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                newNameText = userProfile?.username.orEmpty()
                isEditNameDialogOpen = true
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Username",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Profile Picture with tap to choose from gallery / history
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    if (!isSaving) {
                        isDialogOpen.value = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(userProfile?.profileImg?.ifEmpty { R.drawable.pokequiz } ?: R.drawable.pokequiz)
                    .fallback(R.drawable.pokequiz)
                    .error(R.drawable.pokequiz)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (isSaving) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tap to Change",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons to Pick New Photo or choose from history
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Pick New Photo")
            }

            OutlinedButton(
                onClick = { isDialogOpen.value = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("Saved Photos")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Statistics Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Quiz Statistics",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val games = userProfile?.gamesPlayed ?: 0
                val guesses = userProfile?.totalGuesses ?: 0
                val accuracy = if (guesses > 0) ((games.toFloat() / guesses.toFloat()) * 100).toInt() else 0

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Games Played:", fontWeight = FontWeight.SemiBold)
                    Text(text = "$games", fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total Guesses:", fontWeight = FontWeight.SemiBold)
                    Text(text = "$guesses", fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Accuracy Rating:", fontWeight = FontWeight.SemiBold)
                    Text(text = "$accuracy%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Reset Statistics Button
        OutlinedButton(
            onClick = { isResetConfirmOpen = true },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Quiz Statistics")
        }
    }

    // Dialog: Edit Username
    if (isEditNameDialogOpen) {
        AlertDialog(
            onDismissRequest = { isEditNameDialogOpen = false },
            title = { Text("Edit Trainer Name") },
            text = {
                OutlinedTextField(
                    value = newNameText,
                    onValueChange = { newNameText = it },
                    label = { Text("Trainer Name") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (newNameText.isNotBlank()) {
                        viewModel.updateUsername(newNameText)
                    }
                    isEditNameDialogOpen = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { isEditNameDialogOpen = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Dialog: Confirm Reset Stats
    if (isResetConfirmOpen) {
        AlertDialog(
            onDismissRequest = { isResetConfirmOpen = false },
            title = { Text("Reset Statistics?") },
            text = { Text("Are you sure you want to reset your quiz stats and scoreboard score to 0?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetStats()
                        isResetConfirmOpen = false
                        Toast.makeText(context, "Stats reset to 0.", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { isResetConfirmOpen = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Dialog: Choose Photo from saved pictures
    if (isDialogOpen.value) {
        UserProfileDialog(userProfile, viewModel, isDialogOpen)
    }
}
