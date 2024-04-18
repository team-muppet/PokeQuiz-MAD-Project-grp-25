package com.example.pokequiz.ui.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.service.PokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonService: PokemonService
) : ViewModel() {
    private val _pokemon = MutableLiveData<List<Pokemon>>()
    val pokemon: LiveData<List<Pokemon>> = _pokemon

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            val pokemonList = pokemonService.getPokemon()
            _pokemon.postValue(pokemonList)
        }
    }
}