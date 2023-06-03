package com.adolfo.marvel.common.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adolfo.core.exception.Failure.CustomError
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.marvel.R.drawable
import com.adolfo.marvel.R.string
import com.adolfo.marvel.common.navigation.models.CharacterScreenItem
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModelCompose
import kotlin.system.exitProcess

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModelCompose,
    onCharacterClicked: (Int) -> Unit
) {
    val state by viewModel.characters.collectAsState()
    val listState = rememberLazyListState()
    val isAtBottom by remember { derivedStateOf { !listState.canScrollForward } }

    LaunchedEffect(key1 = isAtBottom) {
        if (isAtBottom && !state.finishPagination) {
            viewModel.getCharacters(true)
        }
    }

    AnimatedContent(targetState = state.isLoading) { isLoading ->
        if (!isLoading) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(id = string.characters_toolbar_title),
                                style = MaterialTheme.typography.h6
                            )
                        }
                    )
                }
            ) { paddingValues ->
                if (state.error != null) {
                    when (state.error?.failure) {
                        is NetworkConnection -> NetworkErrorView(
                            onAccept = { viewModel.getCharacters() },
                            onDecline = { exitProcess(-1) }
                        )

                        is ServerError -> ServerErrorView(
                            onAccept = { viewModel.getCharacters() },
                            onDecline = { exitProcess(-1) }
                        )

                        is CustomError -> {
                            // TODO: Snackbar if pagination
                        }

                        else -> GenericErrorView(
                            onAccept = { viewModel.getCharacters() },
                            onDecline = { exitProcess(-1) }
                        )
                    }
                } else {
                    if (state.characters.isEmpty()) {
                        InformationView(
                            drawableId = drawable.ic_emoji_people,
                            title = stringResource(id = string.characters_emtpy_state_title),
                            description = stringResource(id = string.characters_emtpy_state_desc),
                            onAcceptText = stringResource(id = string.button_retry),
                            onAccept = { viewModel.getCharacters() },
                            onDeclineText = stringResource(id = string.button_exit),
                            onDecline = { exitProcess(-1) }
                        )
                    } else {
                        Column(modifier = modifier.padding(paddingValues)) {
                            LazyColumn(
                                state = listState,
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                items(state.characters, key = { it.id }) { hero ->
                                    CharacterScreenItem(modifier, hero = hero) {
                                        onCharacterClicked(hero.id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Loader(modifier)
        }
    }
}
