package com.example.pokequiz.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int = 0,
    val name: String = "",
    val url: String = "",
    val img: String? = null,
    val types: List<PokemonType> = emptyList()
)

@Serializable
data class PokemonType(
    val slot: Int = 1,
    val type: TypeInfo = TypeInfo()
)

@Serializable
data class TypeInfo(
    val name: String = "",
    val url: String = ""
)
