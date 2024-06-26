package com.adolfo.marvel.features.character.view.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adolfo.core.exception.Failure.CustomError
import com.adolfo.core.exception.Failure.NetworkConnection
import com.adolfo.core.exception.Failure.ServerError
import com.adolfo.marvel.R
import com.adolfo.marvel.R.drawable
import com.adolfo.marvel.R.string
import com.adolfo.marvel.common.navigation.GenericErrorView
import com.adolfo.marvel.common.navigation.InformationView
import com.adolfo.marvel.common.navigation.Loader
import com.adolfo.marvel.common.navigation.NetworkErrorView
import com.adolfo.marvel.common.navigation.ServerErrorView
import com.adolfo.marvel.features.character.view.ui.models.CharacterItemModel
import com.adolfo.marvel.features.character.view.ui.models.CharacterScreenItem
import com.adolfo.marvel.features.character.view.ui.models.CharactersScreenState.LoadingType
import com.adolfo.marvel.features.character.view.ui.models.CharactersScreenState.LoadingType.NORMAL
import com.adolfo.marvel.features.character.view.ui.models.CharactersScreenState.LoadingType.PAGINATION
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import kotlin.system.exitProcess
import kotlinx.coroutines.launch

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel,
    onCharacterClicked: (Int) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.characters.collectAsState()
    val listState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()
    val isAtBottom by remember {
        derivedStateOf { !listState.canScrollForward && listState.layoutInfo.totalItemsCount > 0 }
    }

    LaunchedEffect(key1 = isAtBottom) {
        if (isAtBottom) {
            viewModel.getCharacters(true)
        }
    }

    LaunchedEffect(key1 = state.error?.failure) {
        val failure = state.error?.failure
        if (failure is CustomError) {
            when (failure.code) {
                CustomError.PAGINATION_ERROR -> {
                    snackbarCoroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            context.getString(R.string.characters_pagination_error)
                        )
                    }
                }
            }
        }
    }

    if (state.isLoading != NORMAL) {
        Scaffold(
            scaffoldState = scaffoldState,
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
                        CharactersList(
                            modifier = modifier,
                            paddingValues = paddingValues,
                            listState = listState,
                            characters = state.characters,
                            isLoading = state.isLoading,
                            onCharacterClicked = onCharacterClicked
                        )
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
                    CharactersList(
                        modifier = modifier,
                        paddingValues = paddingValues,
                        listState = listState,
                        characters = state.characters,
                        isLoading = state.isLoading,
                        onCharacterClicked = onCharacterClicked
                    )
                }
            }
        }
    } else {
        Loader(modifier)
    }
}

@Composable
fun CharactersList(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    listState: LazyListState,
    characters: List<CharacterItemModel>,
    isLoading: LoadingType,
    onCharacterClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .paint(
                painterResource(id = drawable.background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(characters, key = { it.id }) { hero ->
                CharacterScreenItem(modifier, hero = hero) {
                    onCharacterClicked(hero.id)
                }
            }

            item(key = isLoading) {
                when (isLoading) {
                    PAGINATION -> {
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(128.dp)
                        ) {
                            Loader(modifier)
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}
