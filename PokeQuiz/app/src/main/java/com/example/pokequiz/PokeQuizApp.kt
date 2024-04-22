package com.example.pokequiz

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokequiz.screens.card_quiz.CardQuiz
import com.example.pokequiz.screens.sign_in.SignInScreen
import com.example.pokequiz.screens.sign_up.SignUpScreen
import com.example.pokequiz.screens.splash.SplashScreen
import com.example.pokequiz.ui.components.navdrawer.NavDrawer
import com.example.pokequiz.screens.pokequiz_home.PokeQuizHome
import com.example.pokequiz.screens.pokemon_details.PokemonDetails
import com.example.pokequiz.screens.pokemon_list.PokemonList
import com.example.pokequiz.screens.scoreboard.Scoreboard
import com.example.pokequiz.screens.silhoutte_quiz.SilhouetteQuiz
import com.example.pokequiz.ui.theme.PokeQuizTheme

@Composable
fun PokeQuizApp(viewModel: PokeQuizAppViewModel = hiltViewModel()) {
    val isAuthenticated = viewModel.isAuthenticated.observeAsState(false)
    val appState = rememberAppState()

    LaunchedEffect(isAuthenticated.value){
        if(!isAuthenticated.value) // If you logout it will clear the navigation stack
        {
            appState.navController.navigate(SPLASH_SCREEN) {
                popUpTo(appState.navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    PokeQuizTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (isAuthenticated.value) {
                NavDrawer(appState) {
                    NavHost(
                        navController = appState.navController,
                        startDestination = SPLASH_SCREEN
                    ) {
                        pokeQuizGraph(appState)
                    }
                }
            } else {
                NavHost(navController = appState.navController, startDestination = SPLASH_SCREEN) {
                    pokeQuizGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        AppState(navController)
    }

fun NavGraphBuilder.pokeQuizGraph(appState: AppState) {
    composable(HOME) { PokeQuizHome() }
    composable(POKEMON_LIST) { PokemonList(appState = appState) }
    composable("$POKEMON_DETAILS$DETAILS_ARG") {
        val pokemonId = it.arguments?.getString("pokemonId") ?: "pokemonId argument missing"
        PokemonDetails(pokemonId)
    }
    composable(POKE_QUIZ1) { Text(text = "Quiz 1") }
    composable(POKE_QUIZ2) { CardQuiz() }
    composable(POKE_QUIZ3) { SilhouetteQuiz() }
    composable(SCOREBOARD) { Scoreboard() }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
}
