package com.example.pokequiz.model.service

import com.example.pokequiz.model.GraphQLQuery
import com.example.pokequiz.model.PokemonDetails
import retrofit2.http.Body
import retrofit2.http.POST

interface DetailsService {
    @POST("graphql/v1beta")
    suspend fun getPokemonDetails(@Body query: GraphQLQuery) : PokemonDetails
}