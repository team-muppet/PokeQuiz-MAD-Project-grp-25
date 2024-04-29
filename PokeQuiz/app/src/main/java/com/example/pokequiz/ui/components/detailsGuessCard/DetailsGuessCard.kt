package com.example.pokequiz.ui.components.detailsGuessCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DetailsGuessCard(text : String, arrowFun: () ->  Int? = { null }, colorFun: () -> Color){
    Card(modifier = Modifier
        .height(75.dp)
        .width(75.dp)
        .padding(5.dp)){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colorFun())
                .border(shape = RoundedCornerShape(10.dp), border = BorderStroke(2.dp, Color.White))
        ){
            if(arrowFun() != null)
            {
                Icon(
                    painter = painterResource(id = arrowFun()!!),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(70.dp)
                        .alpha(0.5f)
                )
            }
            Text(text = text, textAlign = TextAlign.Center, color = Color.White)
        }
    }
}