package com.example.rickyandmortyapp.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.rickyandmortyapp.R
import com.example.rickyandmortyapp.ui.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

object FavoritesScreen : Screen {
    private fun readResolve(): Any = FavoritesScreen

    override val key: ScreenKey
        get() = "Favorites"

    @Composable
    override fun Content() {
        val viewModel: FavoritesViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow
        FavoritesScreenContent(viewModel, navigator)
    }
}

@Composable
fun FavoritesScreenContent(
    viewModel: FavoritesViewModel,
    navigator: Navigator
) {
    val favoriteCharacters by viewModel.favoriteCharacters.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (favoriteCharacters.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_favorites),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(favoriteCharacters.size) { index ->
                    val character = favoriteCharacters[index]
                    CharacterItem(
                        character,
                        onOpenDetails = {
                            navigator.push(CharacterDetailsScreen(character.id))
                        }
                    )
                }
            }
        }
    }
}