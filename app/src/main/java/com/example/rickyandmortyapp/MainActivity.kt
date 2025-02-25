package com.example.rickyandmortyapp

import CharactersScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.rickyandmortyapp.ui.theme.RickyAndMortyAppTheme
import com.example.rickyandmortyapp.ui.view.AppTopBar
import com.example.rickyandmortyapp.ui.view.BottomNavigationBar
import com.example.rickyandmortyapp.ui.view.CharacterDetailsScreen
import com.example.rickyandmortyapp.ui.view.NoInternetScreen
import com.example.rickyandmortyapp.ui.view.SearchScreen
import com.example.rickyandmortyapp.ui.viewmodel.InternetViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickyAndMortyApp()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RickyAndMortyApp() {
    RickyAndMortyAppTheme {
        Navigator(CharactersScreen) { navigator ->
            val currentScreen = navigator.items.lastOrNull() ?: CharactersScreen
            val visibilityComponent =
                currentScreen !is SearchScreen &&
                        currentScreen !is CharacterDetailsScreen &&
                        currentScreen !is NoInternetScreen

            val internetViewModel: InternetViewModel = koinViewModel()
            val isInternetAvailable by internetViewModel.isInternetAvailable.collectAsState()

            LaunchedEffect(isInternetAvailable) {
                if (!isInternetAvailable && currentScreen !is NoInternetScreen) {
                    navigator.push(NoInternetScreen)
                } else if (isInternetAvailable && currentScreen is NoInternetScreen) {
                    navigator.push(CharactersScreen)
                }
            }

            Scaffold(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .fillMaxSize(),
                topBar = {
                    if (visibilityComponent) {
                        AppTopBar(
                            currentScreen = currentScreen,
                            onSearchClick = {
                                if (navigator.items.lastOrNull() !is SearchScreen) {
                                    navigator.push(SearchScreen)
                                }
                            }
                        )
                    }
                },
                bottomBar = {
                    if (visibilityComponent) {
                        BottomNavigationBar(
                            selectedScreen = currentScreen,
                            onScreenSelected = { screen ->
                                if (screen !is SearchScreen) {
                                    navigator.replace(screen)
                                }
                            }
                        )
                    }
                }
            ) { paddingValues ->
                Column(Modifier.padding(paddingValues)) {
                    CurrentScreen()
                }
            }
        }
    }
}