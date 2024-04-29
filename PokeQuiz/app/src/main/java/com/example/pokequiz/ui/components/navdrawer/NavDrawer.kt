package com.example.pokequiz.ui.components.navdrawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokequiz.AppState
import com.example.pokequiz.HOME
import com.example.pokequiz.POKEMON_LIST
import com.example.pokequiz.POKE_QUIZ1
import com.example.pokequiz.POKE_QUIZ2
import com.example.pokequiz.POKE_QUIZ3
import com.example.pokequiz.SCOREBOARD
import com.example.pokequiz.USER_PROFILE
import com.example.pokequiz.screens.user_profile.UserProfileScreen
import compose.icons.TablerIcons
import compose.icons.tablericons.Logout
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
    appState: AppState,
    viewModel: NavDrawerViewModel = hiltViewModel(),
    screenContent: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    fun navAndClose(route: String) {
        scope.launch { drawerState.close() }
        appState.navigate(route);
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    onClick = { navAndClose(HOME) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon List") },
                    selected = false,
                    onClick = { navAndClose(POKEMON_LIST) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon Quiz 1") },
                    selected = false,
                    onClick = { navAndClose(POKE_QUIZ1) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Card Quiz") },
                    selected = false,
                    onClick = { navAndClose(POKE_QUIZ2) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Silhouette Quiz") },
                    selected = false,
                    onClick = { navAndClose(POKE_QUIZ3) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon Quiz Scoreboard") },
                    selected = false,
                    onClick = { navAndClose(SCOREBOARD); }
                )
                NavigationDrawerItem(
                    label = { Text(text = "User Profile") },
                    selected = false,
                    onClick = { navAndClose(USER_PROFILE); }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Sign out") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            viewModel.signOut()
                        }
                    },
                    icon = { Icon(imageVector = TablerIcons.Logout, contentDescription = "Sign out") },
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("PokéQuiz") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                screenContent()
            }
        }
    }
}