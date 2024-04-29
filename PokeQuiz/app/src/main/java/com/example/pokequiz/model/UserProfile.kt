package com.example.pokequiz.model

import com.google.firebase.firestore.DocumentId

data class UserProfile(
    @DocumentId val id: String = "",
    val username: String,
    val profileImg: String,
    val oldProfileImgs: List<String>,
    val game1Score: Double,
    val game2Score: Double,
    val game3Score: Double,
    val userid: String,

)