package com.example.pokequiz.screens.silhoutte_quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.service.PokemonService
import com.example.pokequiz.screens.PokeQuizViewModel
import com.example.pokequiz.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SilhouetteQuizViewModel @Inject constructor(
    private val pokemonService: PokemonService
) : PokeQuizViewModel() {
    private val _pokemonList = mutableStateOf<List<Pokemon>>(listOf())
    val pokemonList : State<List<Pokemon>> = _pokemonList

    init {
        loadPokemon()
    }

    private fun loadPokemon(){
        viewModelScope.launch {
            _pokemonList.value = pokemonService.getPokemon().take(151)
        }
    }

}