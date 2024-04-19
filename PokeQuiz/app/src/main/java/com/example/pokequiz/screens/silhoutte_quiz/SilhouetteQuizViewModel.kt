package com.example.pokequiz.screens.silhoutte_quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _pokemonList = MutableLiveData<List<Pokemon>>(listOf())

    private val _currentPool = MutableLiveData<List<Pokemon>?>(null)
    val currentPool: LiveData<List<Pokemon>?> = _currentPool

    private val _currentPokemon = MutableLiveData<Pokemon?>(null)
    val currentPokemon: LiveData<Pokemon?> = _currentPokemon

    private val _gameState = MutableLiveData<String?>(null)
    val gameState: LiveData<String?> = _gameState

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            _pokemonList.value = pokemonService.getPokemon().take(151)
            shufflePokemon()
        }
    }

    private fun shufflePokemon() {
        _currentPool.value = _pokemonList.value?.shuffled()?.take(4)
        _currentPokemon.value = _currentPool.value?.random()
    }

    fun checkGuess(pokemonName: String) {
        _gameState.value = if (pokemonName == _currentPokemon.value?.name) "You win!" else "You lose!"
    }

    fun resetGame() {
        shufflePokemon()
        _gameState.value = null
    }
}