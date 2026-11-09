package com.example.pokequiz.screens.card_quiz

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.PokemonCard
import com.example.pokequiz.model.service.PokemonService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.model.service.implementation.PTCGServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardQuizViewModel @Inject constructor(
    private val cardService: PTCGServiceImpl,
    private val pokemonService: PokemonService,
    private val profileService: ProfileService
) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>(listOf())

    private val _guessedPokemon = MutableLiveData<List<Pokemon>>(emptyList())
    val guessedPokemon: LiveData<List<Pokemon>> = _guessedPokemon

    private val _gamePokemon = MutableLiveData<List<Pokemon>>(emptyList())
    val gamePokemon: LiveData<List<Pokemon>> = _gamePokemon

    private val _currentCard = MutableLiveData<PokemonCard?>(null)
    val currentCard: LiveData<PokemonCard?> = _currentCard

    private val _currentPokemon = MutableLiveData<Pokemon?>(null)
    val currentPokemon: LiveData<Pokemon?> = _currentPokemon

    private val _gameState = MutableLiveData<Boolean>(false)
    val gameState: LiveData<Boolean> = _gameState

    private val _cardBlur = MutableLiveData<Dp>(15.dp)
    val cardBlur: LiveData<Dp> = _cardBlur

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currentUser = profileService.currentProfile

    init {
        loadCards()
    }

    private fun loadCards() {
        viewModelScope.launch {
            _pokemonList.value = pokemonService.getPokemon().take(151)
            _gamePokemon.value = _pokemonList.value
            pickACard()
        }
    }

    private fun pickACard() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val candidate = _pokemonList.value?.shuffled()?.firstOrNull() ?: Pokemon(id = 1, name = "bulbasaur")
                _currentPokemon.value = candidate
                _currentCard.value = cardService.getGen1Card(candidate.id)
                _cardBlur.value = 15.dp
            } catch (e: Exception) {
                try {
                    _currentPokemon.value = Pokemon(id = 1, name = "bulbasaur")
                    _currentCard.value = cardService.getGen1Card(1)
                    _cardBlur.value = 15.dp
                } catch (ex: Exception) {
                    // ignore
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkGuess(pokemon: Pokemon) {
        viewModelScope.launch {
            val tmp = _guessedPokemon.value.orEmpty().toMutableList()
            tmp.add(pokemon)
            _guessedPokemon.value = tmp

            _gamePokemon.value =
                _pokemonList.value.orEmpty().filter { it !in _guessedPokemon.value.orEmpty() }

            if (_currentPokemon.value?.name == pokemon.name) {
                _gameState.value = true
                _cardBlur.value = 0.dp
                updateProfileWithGame(tmp.size)
            } else {
                _cardBlur.value = _cardBlur.value?.minus(1.dp)
            }
        }
    }

    private suspend fun updateProfileWithGame(guesses: Int) {
        try {
            val profile = _currentUser.first()
            val updatedProfile = profile.copy(
                totalGuesses = (profile.totalGuesses + guesses),
                gamesPlayed = (profile.gamesPlayed + 1)
            )
            profileService.updateProfile(updatedProfile)
        } catch (e: Exception) {
            // ignore
        }
    }

    fun resetGame() {
        _isLoading.value = true
        pickACard()
        _gamePokemon.value = _pokemonList.value
        _guessedPokemon.value = emptyList()
        _gameState.value = false
    }
}
