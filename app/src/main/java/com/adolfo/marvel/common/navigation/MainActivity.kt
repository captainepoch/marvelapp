package com.adolfo.marvel.common.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.adolfo.marvel.features.character.view.ui.CharacterDetailScreen
import com.adolfo.marvel.features.character.view.ui.CharactersScreen
import com.adolfo.marvel.ui.theme.MarvelAppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarvelAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "characters"
                    ) {
                        composable("characters") {
                            CharactersScreen(
                                viewModel = koinViewModel(),
                                onCharacterClicked = {
                                    navController.navigate("character/${it}")
                                }
                            )
                        }
                        composable(
                            route = "character/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType }),
                            enterTransition = {
                                slideInHorizontally { it }
                            },
                            exitTransition = {
                                slideOutHorizontally { -it }
                            },
                            popEnterTransition = {
                                slideInHorizontally { -it }
                            },
                            popExitTransition = {
                                slideOutHorizontally { it }
                            },
                        ) {
                            CharacterDetailScreen(
                                viewModel = koinViewModel(),
                                onBackPressed = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
