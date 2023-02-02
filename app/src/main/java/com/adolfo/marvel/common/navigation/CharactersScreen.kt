package com.adolfo.marvel.common.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adolfo.marvel.common.navigation.models.CharacterScreenItem

@Composable
fun CharactersScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        LazyColumn {
            items(getHeroList()) { hero ->
                CharacterScreenItem(modifier, heroName = hero.name)
            }
        }
    }
}

private fun getHeroList() = List(10) { HeroItem("aaaa") }

data class HeroItem(val name: String)
