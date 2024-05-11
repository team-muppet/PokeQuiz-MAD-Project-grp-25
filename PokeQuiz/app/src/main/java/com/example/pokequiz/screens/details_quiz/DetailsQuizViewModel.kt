package com.example.pokequiz.screens.details_quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.PokeminDetails
import com.example.pokequiz.model.service.PokemonService
import com.example.pokequiz.model.service.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsQuizViewModel @Inject constructor(
    private val pokemonService: PokemonService, private val profileService: ProfileService
) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>(listOf())

    private val _guessedPokemon = MutableLiveData<List<PokeminDetails>>(emptyList())
    val guessedPokemon: LiveData<List<PokeminDetails>> = _guessedPokemon

    private val _gamePokemon = MutableLiveData<List<Pokemon>>(emptyList())
    val gamePokemon: LiveData<List<Pokemon>> = _gamePokemon

    private val _currentDetails = MutableLiveData<PokeminDetails?>(null)
    val currentDetails: LiveData<PokeminDetails?> = _currentDetails

    private val _currentPokemon = MutableLiveData<Pokemon?>(null)
    val currentPokemon: LiveData<Pokemon?> = _currentPokemon

    private val _gameState = MutableLiveData<Boolean>(false)
    val gameState: LiveData<Boolean> = _gameState

    private val _currentUser = profileService.currentProfile


    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            _pokemonList.value = pokemonService.getPokemon().take(151)
            _gamePokemon.value = _pokemonList.value
            shufflePokemon()
        }
    }

    private fun shufflePokemon() {
        viewModelScope.launch {
            _currentPokemon.value = _pokemonList.value?.shuffled()?.first()
            _currentDetails.value = pokemonService.getPokemonDetails(_currentPokemon.value!!.id)
        }
    }

    fun checkGuess(pokemon: Pokemon) {
        viewModelScope.launch {
            // Add pokemon to guessed list
            val tmp = _guessedPokemon.value.orEmpty().toMutableList()
            tmp.add(pokemonService.getPokemonDetails(pokemon.id))
            _guessedPokemon.value = tmp

            val guessedIds = _guessedPokemon.value!!.map { p -> p.id }

            // Update _gamePokemon
            _gamePokemon.value = _pokemonList.value.orEmpty().filter { it.id !in guessedIds }

            // Check if game won
            if (pokemon.name.lowercase() == _currentPokemon.value?.name?.lowercase()) {
                _gameState.value = true
                updateProfileWithGame(tmp.size)
            }
        }
    }

    private suspend fun updateProfileWithGame(guesses: Int)
    {
        val profile = _currentUser.first() // Collect the current profile once
        println("LOPING IN COLLECT")
        val updatedProfile = profile.copy(
            totalGuesses = (profile.totalGuesses + guesses),
            gamesPlayed = (profile.gamesPlayed + 1)
        )
        profileService.updateProfile(updatedProfile)
    }

    fun resetGame() {
        shufflePokemon()
        _gamePokemon.value = _pokemonList.value
        _guessedPokemon.value = emptyList()
        _gameState.value = false
    }
}