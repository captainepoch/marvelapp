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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adolfo.marvel.R
import com.adolfo.marvel.ui.theme.TextStyles

@Composable
fun CharacterScreenItem(
    modifier: Modifier = Modifier,
    hero: CharacterItemModel = CharacterItemModel()
) {
    Box(
        modifier = modifier
            .height(128.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_marvel_logo),
            contentDescription = "Marvel logo",
            modifier = modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = hero.name,
            modifier = modifier
                .wrapContentSize()
                .align(Alignment.BottomStart)
                .padding(4.dp),
            style = TextStyles.CharacterTitle
        )
    }
}

@Preview
@Composable
fun PreviewCharacterScreenItem() {
    CharacterScreenItem(hero = CharacterItemModel("Placeholder"))
}
