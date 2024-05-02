package com.example.pokequiz.model

import com.google.firebase.firestore.DocumentId

data class UserProfile(
    @DocumentId val id: String = "",
    val username: String = "",
    val profileImg: String = "",
    val oldProfileImgs: List<String> = listOf(), // Ensure default value for List<String>
    val gamesPlayed: Int = 0,
    val totalGuesses: Int = 0,
    val game1Score: Double = 0.0,
    val game2Score: Double = 0.0,
    val game3Score: Double = 0.0,
    val userId: String = ""
) {
    constructor() : this("", "", "", listOf(), 0, 0, 0.0, 0.0, 0.0, "")
}
