package com.adolfo.marvel.common.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adolfo.marvel.common.navigation.models.CharacterScreenItem
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModelCompose
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModelCompose = koinViewModel()
) {
    Column(modifier = modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(viewModel.characters.value.characters) { hero ->
                CharacterScreenItem(modifier, hero = hero)
            }
        }
    }
}

@Preview
@Composable
fun PreviewCharactersScreen() {
    CharactersScreen()
}
