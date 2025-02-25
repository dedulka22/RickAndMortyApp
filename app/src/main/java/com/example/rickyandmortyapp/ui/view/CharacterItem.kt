package com.example.rickyandmortyapp.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.rickyandmortyapp.R
import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.ui.viewmodel.FavoritesViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CharacterItem(
    character: Character,
    onOpenDetails: (Character) -> Unit,
    isSearchResult: Boolean = false,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val isFavorite by favoritesViewModel.isFavorite(character.id).collectAsState(initial = false)

    if (isSearchResult) {
        CharacterItemContent(
            character = character,
            isFavorite = isFavorite,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clickable { onOpenDetails(character) }
        )
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .clickable { onOpenDetails(character) }
                .shadow(8.dp, shape = MaterialTheme.shapes.extraLarge),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.surface,
            )
        ) {
            CharacterItemContent(
                character = character,
                isFavorite = isFavorite
            )
        }
    }
}

@Composable
fun CharacterItemContent(
    character: Character,
    isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = character.imageUrl,
            contentDescription = "Character Image",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(
                    text = character.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (isFavorite) {
                    Icon(
                        painter = painterResource(id = R.drawable.favorites_selected),
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(start = 4.dp)
                    )
                }
            }

            Text(
                text = character.status.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}