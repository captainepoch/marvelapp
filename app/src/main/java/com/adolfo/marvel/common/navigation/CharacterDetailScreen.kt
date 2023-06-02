package com.adolfo.marvel.common.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy.ENABLED
import coil.request.ImageRequest.Builder
import com.adolfo.marvel.R.drawable
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModelCompose

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModelCompose,
    onBackPressed: () -> Unit
) {
    val state by viewModel.character.collectAsState()

    Scaffold(
        topBar = {
            CharacterDetailTopAppBar(state.character.name) {
                onBackPressed()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = Builder(LocalContext.current)
                    .data(state.character.image)
                    .fallback(drawable.ic_marvel_logo)
                    .crossfade(true)
                    .diskCachePolicy(ENABLED)
                    .build(),
                placeholder = painterResource(id = drawable.ic_marvel_logo),
                contentDescription = "Hero Image",
                modifier = modifier.wrapContentHeight(),
                contentScale = ContentScale.Fit,
                alignment = Alignment.TopCenter
            )

            Text(
                text = state.character.name,
                modifier = modifier.padding(8.dp)
            )

            BackHandler(onBack = { onBackPressed() })
        }
    }
}

@Composable
fun CharacterDetailTopAppBar(characterName: String, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = characterName,
                style = MaterialTheme.typography.h6
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    )
}
