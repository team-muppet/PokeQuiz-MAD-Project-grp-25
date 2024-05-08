package com.example.pokequiz.model

import com.google.firebase.firestore.DocumentId

data class UserProfile(
    @DocumentId val id: String = "",
    val username: String = "",
    val profileImg: String = "",
    val oldPictures: List<String> = listOf(), // Ensure default value for List<String>
    val gamesPlayed: Int = 0,
    val totalGuesses: Int = 0,
    val userId: String = ""
) {
    constructor() : this("", "", "", listOf(), 0, 0, "")
}
