package com.example.pokequiz.model.service.implementation

import com.example.pokequiz.model.PTCGData
import com.example.pokequiz.model.PokemonCard
import com.example.pokequiz.model.service.PTCGService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

class PTCGServiceImpl @Inject constructor() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.pokemontcg.io/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: PTCGService = retrofit.create(PTCGService::class.java)

    suspend fun getGen1Cards(): List<PokemonCard> {
        try {
            val page1 = service.getGen1Cards(page = 1)
            val page2 = service.getGen1Cards(page = 2)
            val data = page1.data + page2.data
            return data.distinctBy { it.name }
        } catch (e: Exception){
            // Do some error handling
            throw e
        }
    }


}