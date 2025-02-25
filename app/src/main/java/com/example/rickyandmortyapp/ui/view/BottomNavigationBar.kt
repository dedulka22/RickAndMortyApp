package com.example.rickyandmortyapp.ui.view

import CharactersScreen
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.example.rickyandmortyapp.R

@Composable
fun BottomNavigationBar(
    selectedScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    val isCharactersSelected = selectedScreen is CharactersScreen
    val isFavoritesSelected = selectedScreen is FavoritesScreen

    val backgroundColor = MaterialTheme.colorScheme.surface
    val transparentColor = backgroundColor
        .copy(alpha = 0.92f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(
                8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(transparentColor, backgroundColor),
                        startY = -20f,
                        endY = size.height
                    ),
                    topLeft = Offset(0f, -20f)
                )
            }
    ) {
        NavigationBar(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            NavigationBarItem(
                selected = isCharactersSelected,
                onClick = { onScreenSelected(CharactersScreen) },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (isCharactersSelected) R.drawable.characters_active
                            else R.drawable.characters_inactive
                        ),
                        tint = getNavigationItemColor(isCharactersSelected),
                        contentDescription = "Characters"
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.characters),
                        color = getNavigationItemColor(isCharactersSelected)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
            NavigationBarItem(
                selected = isFavoritesSelected,
                onClick = { onScreenSelected(FavoritesScreen) },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (isFavoritesSelected) R.drawable.favorites_selected
                            else R.drawable.favorites_inactive
                        ),
                        tint = getNavigationItemColor(isFavoritesSelected),
                        contentDescription = "Favorites"
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.favorites),
                        color = getNavigationItemColor(isFavoritesSelected)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun getNavigationItemColor(isSelected: Boolean) =
    if (isSystemInDarkTheme()) {
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    } else {
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface
    }