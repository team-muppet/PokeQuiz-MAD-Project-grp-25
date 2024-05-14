package com.example.pokequiz.screens.pokemon_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokequiz.model.PokeminDetails
import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.service.PokemonService
import com.example.pokequiz.model.service.ProfileService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonService: PokemonService,
    private val profileService: ProfileService
) : ViewModel() {
    private val _currentDetails = MutableLiveData<PokeminDetails?>(null)
    val currentDetails: LiveData<PokeminDetails?> = _currentDetails

    private val _pokemonList = MutableLiveData<List<Pokemon>?>(null)
    val pokemonList: LiveData<List<Pokemon>?> = _pokemonList

    fun loadPokemonDetails(pokemonId: Int) {
        viewModelScope.launch {
            _currentDetails.value = pokemonService.getPokemonDetails(pokemonId)

            _pokemonList.value = pokemonService.getPokemon()
        }
    }
}
