package com.adolfo.marvel.common.navigation

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adolfo.marvel.R
import com.adolfo.marvel.common.navigation.models.CharacterScreenItem
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModelCompose

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
        if (isAtBottom) {
            viewModel.getCharacters(true)
        }
    }

    if (state.isLoading) {
        Loader(modifier)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.characters_toolbar_title),
                            style = MaterialTheme.typography.h6
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = modifier.padding(paddingValues)) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
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
