package com.example.rickyandmortyapp.ui.view

import CharactersScreen
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.rickyandmortyapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentScreen: Screen,
    characterName: String? = null,
    searchQuery: String? = null,
    onSearchQueryChange: ((String) -> Unit)? = null,
    onSearchClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    actions: @Composable (() -> Unit)? = null
) {
    Surface(shadowElevation = 4.dp) {
        TopAppBar(
            title = {
                when (currentScreen) {
                    is SearchScreen -> {
                        OutlinedTextField(
                            value = searchQuery.orEmpty(),
                            onValueChange = { newQuery ->
                                onSearchQueryChange?.invoke(newQuery)
                            },
                            placeholder = { Text(stringResource(R.string.search_placeholder)) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                if (searchQuery?.isNotEmpty() == true) {
                                    IconButton(onClick = {
                                        if (onSearchQueryChange != null) {
                                            onSearchQueryChange("")
                                        }
                                    }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Clear text")
                                    }
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                disabledBorderColor = Color.Transparent
                            )
                        )
                    }

                    is CharacterDetailsScreen -> characterName?.let { Text(it) }
                    else -> Text(currentScreen.key)
                }
            },
            navigationIcon = {
                if (currentScreen is SearchScreen || currentScreen is CharacterDetailsScreen) {
                    IconButton(onClick = onBackClick) {
                        Icon(painter = painterResource(R.drawable.arrow_back), contentDescription = "Back")
                    }
                }
            },
            actions = {
                if (currentScreen is CharactersScreen) {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            painterResource(R.drawable.search),
                            contentDescription = "Search"
                        )
                    }
                }
                actions?.invoke()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
    }
}