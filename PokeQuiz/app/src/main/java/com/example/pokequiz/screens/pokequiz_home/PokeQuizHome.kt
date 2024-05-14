package com.example.pokequiz.screens.pokequiz_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokequiz.R

@Composable
fun PokeQuizHome(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to PokéQuiz!", Modifier.padding(10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.pokequiz),
                contentDescription = "PokeQuiz Icon",
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp)
            )
        }
    }
}