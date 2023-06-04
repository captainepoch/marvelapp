package com.adolfo.marvel.features.character.view.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy.ENABLED
import coil.request.ImageRequest.Builder
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.core.extensions.isEmptyOrBlank
import com.adolfo.marvel.R
import com.adolfo.marvel.R.drawable
import com.adolfo.marvel.common.navigation.GenericErrorView
import com.adolfo.marvel.common.navigation.Loader
import com.adolfo.marvel.common.navigation.NetworkErrorView
import com.adolfo.marvel.common.navigation.ServerErrorView
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModelCompose
import kotlin.system.exitProcess
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModelCompose = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.character.collectAsState()

    AnimatedContent(targetState = state.isLoading) { isLoading ->
        if (!isLoading) {
            Scaffold(
                topBar = {
                    CharacterDetailTopAppBar(state.character.name) {
                        onBackPressed()
                    }
                }
            ) { paddingValues ->
                if (state.error != null) {
                    when (state.error?.failure) {
                        is NetworkConnection -> NetworkErrorView(
                            onAccept = { viewModel.getCharacterDetail() },
                            onDecline = { exitProcess(-1) }
                        )

                        is ServerError -> ServerErrorView(
                            onAccept = { viewModel.getCharacterDetail() },
                            onDecline = { exitProcess(-1) }
                        )

                        else -> GenericErrorView(
                            onAccept = { viewModel.getCharacterDetail() },
                            onDecline = { exitProcess(-1) }
                        )
                    }
                } else {
                    CharacterDetail(
                        modifier,
                        paddingValues,
                        state.character.image,
                        state.character.description
                    ) {
                        onBackPressed()
                    }
                }
            }
        } else {
            Loader(modifier)
        }
    }
}

@Composable
fun CharacterDetail(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    image: String,
    description: String,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            AsyncImage(
                model = Builder(LocalContext.current)
                    .data(image)
                    .fallback(drawable.ic_marvel_logo)
                    .crossfade(true)
                    .diskCachePolicy(ENABLED)
                    .dispatcher(Dispatchers.IO)
                    .build(),
                placeholder = painterResource(id = drawable.ic_marvel_logo),
                contentDescription = "Hero Image",
                modifier = modifier.wrapContentHeight()
            )
        }

        Text(
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            text = if (description.isEmptyOrBlank()) {
                stringResource(id = R.string.character_detail_no_description)
            } else {
                description
            }
        )

        BackHandler(onBack = { onBack() })
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

@Preview
@Composable
fun CharacterDetailPreview() {
    CharacterDetail(
        modifier = Modifier,
        paddingValues = PaddingValues(),
        image = "",
        description = "Description of the character for the preview, to see if it looks good or not.",
    ) {}
}
