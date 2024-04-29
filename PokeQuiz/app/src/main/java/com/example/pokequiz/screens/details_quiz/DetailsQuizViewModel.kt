package com.example.pokequiz.screens.details_quiz

import androidx.lifecycle.ViewModel
import com.example.pokequiz.model.service.PokemonService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsQuizViewModel @Inject constructor(
    private val pokemonService: PokemonService
) : ViewModel() {

}