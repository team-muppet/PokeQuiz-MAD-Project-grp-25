package com.example.pokequiz.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val url: String,
    val img: String?,
    val types: List<PokemonType>
)

@Serializable
data class PokemonType(
    val slot: Int,
    val type: TypeInfo
)

@Serializable
data class TypeInfo(
    val name: String,
    val url: String
)