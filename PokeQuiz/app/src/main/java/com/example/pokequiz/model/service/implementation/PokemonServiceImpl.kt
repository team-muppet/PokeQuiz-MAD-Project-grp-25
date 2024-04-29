package com.example.pokequiz.model.service.implementation

import android.content.Context
import com.example.pokequiz.model.GraphQLQuery
import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.PokeminDetails
import com.example.pokequiz.model.PokemonSpecies
import com.example.pokequiz.model.service.DetailsService
import com.example.pokequiz.model.service.PokemonService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class PokemonServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) :PokemonService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://beta.pokeapi.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: DetailsService = retrofit.create(DetailsService::class.java)

    override suspend fun getPokemon(): List<Pokemon> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("pokemon.min.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<List<Pokemon>>(jsonString)
    }

    override suspend fun getPokemonDetails(pokemonId : Int) : PokeminDetails{
        val query = """
                query DetailsQuery {
                  pokemon_v2_pokemon(where: {id: {_eq: ${pokemonId}}}) {
                    id
                    name
                    height
                    weight
                    pokemon_v2_pokemontypes {
                      pokemon_v2_type {
                        name
                      }
                    }
                  }
                  pokemon_v2_pokemonhabitat(where: {pokemon_v2_pokemonspecies: {id: {_eq: ${pokemonId}}}}) {
                    name
                  }
                  pokemon_v2_pokemoncolor(where: {pokemon_v2_pokemonspecies: {id: {_eq: ${pokemonId}}}}) {
                    name
                  }
                  pokemon_v2_evolutionchain(where: {pokemon_v2_pokemonspecies: {id: {_eq: ${pokemonId}}}}) {
                    pokemon_v2_pokemonspecies{
                    	name
                    }
                  }
                }
            """.trimIndent()

        try {
            val res = service.getPokemonDetails(GraphQLQuery(query))
            return PokeminDetails(
                name = res.data.pokemon_v2_pokemon[0].name,
                id = res.data.pokemon_v2_pokemon[0].id,
                type1 = res.data.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes[0].pokemon_v2_type.name,
                type2 = if(res.data.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes.size == 2) res.data.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes[1].pokemon_v2_type.name else "None",
                habitat = res.data.pokemon_v2_pokemonhabitat[0].name,
                color = res.data.pokemon_v2_pokemoncolor[0].name,
                evolutionStage = res.data.pokemon_v2_evolutionchain[0].pokemon_v2_pokemonspecies.indexOf(PokemonSpecies(name = res.data.pokemon_v2_pokemon[0].name)) + 1,
                height = (res.data.pokemon_v2_pokemon[0].height*10),
                weight = (res.data.pokemon_v2_pokemon[0].weight/10)
            )
        }catch (e: Exception){
            // Do some error handling
            throw e
        }
    }
}