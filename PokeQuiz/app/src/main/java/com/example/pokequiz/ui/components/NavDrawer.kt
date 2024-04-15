package com.example.pokequiz.ui.components

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
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(navController : NavHostController, screenContent : @Composable () -> Unit){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    fun navAndClose(route : String){
        scope.launch { drawerState.close() }
        navController.navigate(route);
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    onClick = { navAndClose("home") }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon List") },
                    selected = false,
                    onClick = { navAndClose("pokemonList") }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon Quiz 1") },
                    selected = false,
                    onClick = { navAndClose("pokeQuiz1") }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon Quiz 2") },
                    selected = false,
                    onClick = { navAndClose("pokeQuiz2") }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon Quiz 3") },
                    selected = false,
                    onClick = { navAndClose("pokeQuiz3") }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Pokémon Quiz Scoreboard") },
                    selected = false,
                    onClick = { navAndClose("scoreboard"); }
                )
            }
        }
    ) {
        Scaffold (
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
        ){ contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                screenContent()
            }
        }
    }
}