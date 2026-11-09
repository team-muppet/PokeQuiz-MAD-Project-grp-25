package com.example.pokequiz.model.service.implementation

import com.example.pokequiz.model.PokemonCard
import com.example.pokequiz.model.service.PTCGService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class PTCGServiceImpl @Inject constructor() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.pokemontcg.io/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: PTCGService = retrofit.create(PTCGService::class.java)

    suspend fun getGen1Card(pokemonId: Int): PokemonCard {
        try {
            val res = service.getCards(
                query = "nationalPokedexNumbers:[${pokemonId} TO ${pokemonId}] set.series:\"E-Card\" supertype:\"Pokémon\"",
                select = "name,nationalPokedexNumbers,images",
                orderBy = "nationalPokedexNumbers",
                page = 1
            )
            if (res.data.isNotEmpty()) {
                return res.data[0]
            }

            // Fallback: any card for this Pokémon if not in E-Card series
            val fallbackRes = service.getCards(
                query = "nationalPokedexNumbers:[${pokemonId} TO ${pokemonId}] supertype:\"Pokémon\"",
                select = "name,nationalPokedexNumbers,images",
                orderBy = "nationalPokedexNumbers",
                page = 1
            )
            if (fallbackRes.data.isNotEmpty()) {
                return fallbackRes.data[0]
            }
            throw NoSuchElementException("No card found for Pokemon ID $pokemonId")
        } catch (e: Exception) {
            throw e
        }
    }
}
