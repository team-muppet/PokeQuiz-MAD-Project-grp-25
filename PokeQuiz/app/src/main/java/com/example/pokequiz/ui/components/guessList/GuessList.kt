package com.example.pokequiz.ui.components.guessList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.R
import com.example.pokequiz.model.Pokemon

@Composable
fun GuessList(modifier: Modifier = Modifier, guessedPokemon: List<Pokemon>?, currentPokemon: Pokemon?){
    LazyColumn(modifier = modifier) {
        items(guessedPokemon.orEmpty().reversed()){
            ListItem(
                colors = ListItemColors(
                    containerColor = if(it.name == currentPokemon?.name){
                        Color(0xFF35B956)
                    }else{
                        Color(0xFFDF5858)
                    },
                    disabledHeadlineColor = Color.Transparent,
                    disabledLeadingIconColor = Color.Transparent,
                    disabledTrailingIconColor = Color.Transparent,
                    headlineColor = Color.White,
                    leadingIconColor = Color.Transparent,
                    overlineColor = Color.Transparent,
                    supportingTextColor = Color.Transparent,
                    trailingIconColor = Color.Transparent
                ),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(shape = RoundedCornerShape(10.dp), border = BorderStroke(2.dp, Color.White)),
                headlineContent = { Text(it.name.replaceFirstChar { it.titlecase() }) },
                leadingContent = {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("file:///android_asset/images/${it.id}.svg")
                            .fallback(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = "",
                        loading = { CircularProgressIndicator(modifier = Modifier.align(
                            Alignment.Center)) },
                        modifier = Modifier.size(50.dp)
                    )
                }
            )
        }
    }
}