package com.example.pokequiz.model

import kotlinx.serialization.Serializable

@Serializable
data class PTCGData(
    val data: List<PokemonCard>,
    val page: Int,
    val pageSize: Int,
    val count: Int,
    val totalCount: Int
)

@Serializable
data class PokemonCard(
    val name: String,
    val nationalPokedexNumbers: Array<String>,
    val images: CardImages
)

@Serializable
data class CardImages(
    val small: String,
    val large: String
)