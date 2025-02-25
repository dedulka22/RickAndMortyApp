package com.example.rickyandmortyapp.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.rickyandmortyapp.R
import com.example.rickyandmortyapp.ui.viewmodel.FavoritesViewModel
import com.example.rickyandmortyapp.ui.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

object SearchScreen : Screen {
    private fun readResolve(): Any = SearchScreen

    @Composable
    override fun Content() {
        val viewModel: SearchViewModel = koinViewModel()
        val favoritesViewModel: FavoritesViewModel = koinViewModel()

        SearchScreenContent(
            searchViewModel = viewModel,
            favoritesViewModel = favoritesViewModel
        )
    }
}

@Composable
fun SearchScreenContent(
    searchViewModel: SearchViewModel,
    favoritesViewModel: FavoritesViewModel
) {
    val query by searchViewModel.searchQuery.collectAsState()
    val searchResults = searchViewModel.characters.collectAsLazyPagingItems()
    val focusManager = LocalFocusManager.current
    val navigator = LocalNavigator.currentOrThrow

    AppTopBar(
        currentScreen = SearchScreen,
        searchQuery = query,
        onSearchQueryChange = { newQuery ->
            searchViewModel.onSearchQueryChanged(newQuery)
        },
        onBackClick = {
            focusManager.clearFocus()
            navigator.pop()
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (searchResults.itemCount > 0) {
            LazyColumn {
                items(searchResults.itemCount) { index ->
                    val character = searchResults[index]
                    character?.let { item ->
                        CharacterItem(
                            character = item,
                            onOpenDetails = {
                                navigator.push(CharacterDetailsScreen(item.id))
                            },
                            isSearchResult = true
                        )
                    }
                }
            }
        } else {
            Text(
                stringResource(id = R.string.no_characters),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    }
}