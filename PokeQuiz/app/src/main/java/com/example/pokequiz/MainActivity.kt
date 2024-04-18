package com.example.pokequiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //configureFirebaseServices()
        setContent {
            PokeQuizApp();
        }
    }

    /*private fun configureFirebaseServices() {
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator(LOCALHOST, AUTH_PORT)
            Firebase.firestore.useEmulator(LOCALHOST, FIRESTORE_PORT)
        }
    }*/

}