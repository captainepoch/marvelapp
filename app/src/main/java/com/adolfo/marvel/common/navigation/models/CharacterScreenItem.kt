package com.adolfo.marvel.common.navigation.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adolfo.marvel.R

@Composable
fun CharacterScreenItem(
    modifier: Modifier = Modifier,
    heroName: String = "-"
) {
    Box(
        modifier = modifier
            .height(128.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_marvel_logo),
            contentDescription = "Marvel logo",
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = heroName,
            modifier = modifier
                .wrapContentSize()
                .align(Alignment.BottomStart)
                .padding(4.dp)
        )
    }
}

@Preview
@Composable
fun PreviewCharacterScreenItem() {
    CharacterScreenItem(heroName = "Placeholder")
}
