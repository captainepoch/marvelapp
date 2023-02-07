package com.adolfo.marvel.common.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest.Builder
import com.adolfo.marvel.R.drawable

@Composable
fun CharacterDetailScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        AsyncImage(
            model = Builder(LocalContext.current)
                //.data(hero.image)
                .fallback(drawable.ic_marvel_logo)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = drawable.ic_marvel_logo),
            contentDescription = "Marvel logo",
            modifier = modifier.wrapContentHeight(),
            contentScale = ContentScale.Fit,
            alignment = Alignment.TopCenter
        )
        Text(
            text = "Hero description",
            modifier = modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewCharacterDetailScreen() {
    CharacterDetailScreen()
}
