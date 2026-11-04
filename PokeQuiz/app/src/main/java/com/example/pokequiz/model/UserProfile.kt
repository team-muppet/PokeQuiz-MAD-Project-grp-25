package com.example.pokequiz.model

data class UserProfile(
    val id: String = "",
    val username: String = "",
    val profileImg: String = "",
    val oldPictures: List<String> = listOf(),
    val gamesPlayed: Int = 0,
    val totalGuesses: Int = 0,
    val userId: String = ""
)
