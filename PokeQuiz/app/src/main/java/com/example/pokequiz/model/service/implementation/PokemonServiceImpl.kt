package com.example.pokequiz.model.service.implementation

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.example.pokequiz.DetailsQuery
import com.example.pokequiz.model.Pokemin
import com.example.pokequiz.model.PokeminDetails
import com.example.pokequiz.model.Pokemon
import com.example.pokequiz.model.service.PokemonService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PokemonServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) :PokemonService {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
        .build()

    override suspend fun getPokemon(): List<Pokemon> = withContext(Dispatchers.IO) {
        val jsonString = context.assets.open("pokemon.min.json").bufferedReader().use { it.readText() }
        Json.decodeFromString<List<Pokemon>>(jsonString)
    }

    override suspend fun getPokemonDetails(pokemonId: Int): PokeminDetails {
        val response = apolloClient.query(DetailsQuery(pokemonId)).execute()

        val pokemonDetails = response.data?.let {
            val spritesList = mutableListOf<String>()

            // Recursive function to traverse the JSON tree and add all non-null sprite URLs to the list
            fun addNonNullSprites(spriteMap: Map<String, Any?>) {
                spriteMap.forEach { (_, value) ->
                    when (value) {
                        is String -> spritesList.add(value)
                        is Map<*, *> -> addNonNullSprites(value as Map<String, Any?>)
                    }
                }
            }

            // Ensure the sprites map is not null and call the addNonNullSprites function
            val spritesMap = response.data?.pokemon_v2_pokemonsprites?.firstOrNull()?.sprites
            if (spritesMap != null && spritesMap is Map<*, *>) {
                addNonNullSprites(spritesMap as Map<String, Any?>)
            }

            PokeminDetails(
                name = it.pokemon_v2_pokemon[0].name,
                id = it.pokemon_v2_pokemon[0].id,
                types = it.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes.mapNotNull { typeInfo ->
                    typeInfo.pokemon_v2_type?.name
                },
                type1 = it.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes[0].pokemon_v2_type?.name ?: "None",
                type2 = if (it.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes.size == 2) it.pokemon_v2_pokemon[0].pokemon_v2_pokemontypes[1].pokemon_v2_type?.name ?: "None" else "None",
                habitat = it.pokemon_v2_pokemonhabitat.firstOrNull()?.name ?: "Unknown",
                color = it.pokemon_v2_pokemoncolor[0].name,
                evolutionChain = it.pokemon_v2_evolutionchain[0].pokemon_v2_pokemonspecies.map { species ->
                    Pokemin(id = species.id, name = species.name)
                },
                evolutionStage = it.pokemon_v2_evolutionchain[0].pokemon_v2_pokemonspecies.indexOfFirst { species -> species.name == it.pokemon_v2_pokemon[0].name } + 1,
                height = (it.pokemon_v2_pokemon[0].height!! * 10),
                weight = (it.pokemon_v2_pokemon[0].weight!! / 10),
                cry = (response.data?.pokemon_v2_pokemoncries?.firstOrNull()?.cries as? Map<*, *>)?.get("latest") as? String ?,
                sprites = spritesList,
            )
        }
        return pokemonDetails!!
    }
}