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
import com.example.pokequiz.model.service.implementation.PTCGServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardQuizViewModel @Inject constructor(
    private val cardService : PTCGServiceImpl,
    private val pokemonService: PokemonService
) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>(listOf())

    private val _cardList = MutableLiveData<List<PokemonCard>>(emptyList())
    val cardList: LiveData<List<PokemonCard>> = _cardList

    private val _guessedPokemon = MutableLiveData<List<Pokemon>>(emptyList())
    val guessedPokemon: LiveData<List<Pokemon>> = _guessedPokemon

    private val _gamePokemon = MutableLiveData<List<Pokemon>>(emptyList())
    val gamePokemon: LiveData<List<Pokemon>> = _gamePokemon

    private val _currentCard = MutableLiveData<PokemonCard?>(null)
    val currentPokemon: LiveData<PokemonCard?> = _currentCard

    private val _gameState = MutableLiveData<Boolean>(false)
    val gameState: LiveData<Boolean> = _gameState

    private val _cardBlur = MutableLiveData<Dp>(15.dp)
    val cardBlur: LiveData<Dp> = _cardBlur

    init {
        loadCards()
    }

    private fun loadCards(){
        viewModelScope.launch {
            _cardList.value = cardService.getGen1Cards()
            _pokemonList.value = pokemonService.getPokemon().take(151)
            updateNames()
            _gamePokemon.value = _pokemonList.value
            pickACard()
        }
    }

    private fun updateNames(){
        val tmp = _pokemonList.value?.map {
            Pokemon(
                id = it.id,
                name = _cardList.value?.get(it.id-1)?.name ?: it.name,
                url = it.url,
                img = it.img,
                types = it.types
            )
        }
        _pokemonList.value = tmp.orEmpty()
    }

    private fun pickACard(){
        _currentCard.value = _cardList.value?.shuffled()?.first()
        _cardBlur.value = 15.dp
    }

    fun checkGuess(pokemon: Pokemon) {
        // Add pokemon to guessed list
        val tmp = _guessedPokemon.value.orEmpty().toMutableList()
        tmp.add(pokemon)
        _guessedPokemon.value = tmp

        // Update _gamePokemon
        _gamePokemon.value = _pokemonList.value.orEmpty().filter { it !in _guessedPokemon.value.orEmpty() }

        // Check if game won
        if (_currentCard.value?.name?.lowercase()?.contains(pokemon.name.lowercase()) == true){
            _gameState.value = true
            _cardBlur.value = 0.dp
        };
        else _cardBlur.value = _cardBlur.value?.minus(1.dp)
    }

    fun resetGame() {
        pickACard()
        _gamePokemon.value = _pokemonList.value
        _guessedPokemon.value = emptyList()
        _gameState.value = false
    }
}