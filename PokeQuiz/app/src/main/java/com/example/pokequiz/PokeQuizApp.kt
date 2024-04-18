package com.example.pokequiz

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokequiz.ui.components.NavDrawer
import com.example.pokequiz.ui.components.PokeQuizHome
import com.example.pokequiz.ui.components.PokemonDetails
import com.example.pokequiz.ui.components.PokemonList
import com.example.pokequiz.ui.components.Scoreboard
import com.example.pokequiz.ui.theme.PokeQuizTheme

@Composable
fun PokeQuizApp(){
    PokeQuizTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState();

            NavDrawer(appState){
                NavHost(navController = appState.navController, startDestination = "home"){
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

fun NavGraphBuilder.pokeQuizGraph(appState: AppState){
    composable(HOME){ PokeQuizHome() }
    composable(POKEMON_LIST){ PokemonList(appState = appState) }
    composable("$POKEMON_DETAILS$DETAILS_ARG"){
        val pokemonId = it.arguments?.getString("pokemonId") ?: "pokemonId argument missing"
        PokemonDetails(pokemonId)
    }
    composable(POKE_QUIZ1){ Text(text = "Quiz 1") }
    composable(POKE_QUIZ2){ Text(text = "Quiz 2") }
    composable(POKE_QUIZ3){ Text(text = "Quiz 3") }
    composable(SCOREBOARD){ Scoreboard() }
}
