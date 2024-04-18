package com.example.pokequiz

import android.os.Bundle
import android.telecom.Call.Details
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokequiz.ui.components.NavDrawer
import com.example.pokequiz.ui.components.PokeQuizHome
import com.example.pokequiz.ui.components.PokemonDetails
import com.example.pokequiz.ui.components.PokemonList
import com.example.pokequiz.ui.components.Scoreboard
import com.example.pokequiz.ui.theme.PokeQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //val appState = rememberAppState();
                    val navController = rememberNavController();
                    NavDrawer(navController){
                        NavHost(navController = navController, startDestination = "home"){
                            composable("home"){ PokeQuizHome() }
                            composable("pokemonList"){ PokemonList(navController = navController) }
                            composable("pokemonDetails/{pokemonId}"){
                                val pokemonId = it.arguments?.getString("pokemonId") ?: "pokemonId argument missing"
                                PokemonDetails(pokemonId)
                            }
                            composable("pokeQuiz1"){ Text(text = "Quiz 1") }
                            composable("pokeQuiz2"){ Text(text = "Quiz 2") }
                            composable("pokeQuiz3"){ Text(text = "Quiz 3") }
                            composable("scoreboard"){ Scoreboard() }
                        }
                    }
                }
            }
        }
    }
}