package com.example.pokequiz.model.service

import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.PokeminDetails

interface PokemonService {
    suspend fun getPokemon() : List<Pokemon>

    suspend fun getPokemonDetails(pokemonId : Int) : PokeminDetails
}