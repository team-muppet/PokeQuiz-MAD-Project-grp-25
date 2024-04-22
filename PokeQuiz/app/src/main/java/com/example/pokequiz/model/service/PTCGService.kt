package com.example.pokequiz.model.service

import com.example.pokequiz.model.PTCGData
import com.example.pokequiz.model.PokemonCard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PTCGService {
    @GET("cards")
    suspend fun getGen1Cards(
        @Query("q") query: String = "nationalPokedexNumbers:[1 TO 151] set.series:\"E-Card\" supertype:\"Pokémon\"",
        @Query("select") select: String = "name,nationalPokedexNumbers,images",
        @Query("orderBy") orderBy: String = "nationalPokedexNumbers",
        @Query("page") page: Int = 1
    ): PTCGData
}