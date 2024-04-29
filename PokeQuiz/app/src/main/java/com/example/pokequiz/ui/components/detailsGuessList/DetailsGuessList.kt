package com.example.pokequiz.ui.components.detailsGuessList

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokequiz.R
import com.example.pokequiz.model.PokeminDetails
import com.example.pokequiz.ui.components.detailsGuessCard.DetailsGuessCard

@Composable
fun DetailsGuessList(modifier: Modifier = Modifier, guessedPokemon: List<PokeminDetails>, current: PokeminDetails){
    val scrollState = rememberScrollState()

    LazyColumn(modifier = modifier) {
        val wrong = Color(0xFFDF5858)
        val right = Color(0xFF35B956)
        val up = R.drawable.round_arrow_upward_24
        val down = R.drawable.round_arrow_downward_24

        items(guessedPokemon.orEmpty().reversed()){
            ListItem(
                headlineContent = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(scrollState)
                    ) {
                        DetailsGuessCard(
                            text = it.type1.replaceFirstChar { it.titlecase() },
                            colorFun = {
                                when{
                                    it.type1 == current.type1 -> right
                                    else -> wrong
                                }
                            }
                        )
                        DetailsGuessCard(
                            text = it.type2.replaceFirstChar { it.titlecase() },
                            colorFun = {
                                when{
                                    it.type2 == current.type2 -> right
                                    else -> wrong
                                }
                            }
                        )
                        DetailsGuessCard(
                            text = it.habitat.replaceFirstChar { it.titlecase() },
                            colorFun = {
                                when{
                                    it.habitat == current.habitat -> right
                                    else -> wrong
                                }
                            }
                        )
                        DetailsGuessCard(
                            text = it.color.replaceFirstChar { it.titlecase() },
                            colorFun = {
                                when{
                                    it.color == current.color -> right
                                    else -> wrong
                                }
                            }
                        )
                        DetailsGuessCard(
                            text = it.evolutionStage.toString(),
                            colorFun = {
                                when{
                                    it.evolutionStage == current.evolutionStage -> right
                                    else -> wrong
                                }
                            },
                            arrowFun = {
                                when{
                                    it.evolutionStage < current.evolutionStage -> up
                                    it.evolutionStage > current.evolutionStage -> down
                                    else -> null
                                }
                            }
                        )
                        DetailsGuessCard(
                            text = it.height.toString() + "cm",
                            colorFun = {
                                when{
                                    it.height == current.height -> right
                                    else -> wrong
                                }
                            },
                            arrowFun = {
                                when{
                                    it.height < current.height -> up
                                    it.height > current.height -> down
                                    else -> null
                                }
                            }
                        )
                        DetailsGuessCard(
                            text = it.weight.toString() + "kg",
                            colorFun = {
                                when{
                                    it.weight == current.weight -> right
                                    else -> wrong
                                }
                            },
                            arrowFun = {
                                when{
                                    it.weight < current.weight -> up
                                    it.weight > current.weight -> down
                                    else -> null
                                }
                            }
                        )
                    }
                },
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