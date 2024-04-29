package com.example.pokequiz.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetails(
    val data: PokemonData
)

@Serializable
data class PokemonData(
    val pokemon_v2_pokemon: List<PokemonInfo>,
    val pokemon_v2_pokemonhabitat: List<PokemonHabitat>,
    val pokemon_v2_pokemoncolor: List<PokemonColor>,
    val pokemon_v2_evolutionchain: List<EvolutionChain>
)

@Serializable
data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val pokemon_v2_pokemontypes: List<TypeInformation>
)

@Serializable
data class TypeInformation(
    val pokemon_v2_type: Type
)

@Serializable
data class Type(
    val name: String
)

@Serializable
data class PokemonHabitat(
    val name: String
)

@Serializable
data class PokemonColor(
    val name: String
)

@Serializable
data class EvolutionChain(
    val pokemon_v2_pokemonspecies: List<PokemonSpecies>
)

@Serializable
data class PokemonSpecies(
    val name: String
)

data class PokeminDetails(
    val name: String,
    val id: Int,
    val type1: String,
    val type2: String,
    val habitat: String,
    val color: String,
    val evolutionStage: Int,
    val height: Int,
    val weight: Int
)