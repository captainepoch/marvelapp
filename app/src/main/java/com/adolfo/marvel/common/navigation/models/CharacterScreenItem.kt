package com.adolfo.marvel.common.navigation.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy.ENABLED
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.adolfo.marvel.R
import com.adolfo.marvel.ui.theme.TextStyles
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterScreenItem(
    modifier: Modifier = Modifier,
    hero: CharacterItemModel = CharacterItemModel(),
    onClick: () -> Unit = {}
) {
    var imageContentScale by remember { mutableStateOf(ContentScale.FillBounds) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp),
        border = BorderStroke(width = 1.dp, color = Color.White),
        onClick = onClick
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(hero.image)
                    .fallback(R.drawable.ic_marvel_logo)
                    .crossfade(true)
                    .diskCachePolicy(ENABLED)
                    .dispatcher(Dispatchers.IO)
                    .listener(object : ImageRequest.Listener {

                        override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                            super.onSuccess(request, result)

                            imageContentScale = ContentScale.Crop
                        }
                    })
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_marvel_logo),
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
                contentScale = imageContentScale
            )
            Text(
                text = hero.name,
                modifier = modifier
                    .wrapContentSize()
                    .padding(4.dp)
                    .align(Alignment.BottomStart),
                style = TextStyles.CharacterTitle
            )
        }
    }
}

@Preview
@Composable
fun PreviewCharacterScreenItem() {
    CharacterScreenItem(hero = CharacterItemModel(-1, "Placeholder", ""))
}
