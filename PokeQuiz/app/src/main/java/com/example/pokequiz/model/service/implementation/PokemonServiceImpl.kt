package com.example.pokequiz.model.service.implementation

import android.content.Context
import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.service.PokemonService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PokemonServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PokemonService {
    override suspend fun getPokemon(): List<Pokemon> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("pokemon.min.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<List<Pokemon>>(jsonString)
    }
}