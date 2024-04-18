package com.example.pokequiz.model.service

import com.example.pokequiz.model.Pokemon

interface PokemonService {
    suspend fun getPokemon() : List<Pokemon>
}