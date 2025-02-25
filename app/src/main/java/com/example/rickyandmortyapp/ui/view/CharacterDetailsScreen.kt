package com.example.rickyandmortyapp.ui.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.example.rickyandmortyapp.R
import com.example.rickyandmortyapp.ui.theme.Typography
import com.example.rickyandmortyapp.ui.viewmodel.CharacterDetailViewModel
import org.koin.androidx.compose.koinViewModel

data class CharacterDetailsScreen(
    val characterId: Int
) : Screen {

    @Composable
    override fun Content() {
        val characterDetailViewModel: CharacterDetailViewModel = koinViewModel()

        CharacterDetailsScreenContent(
            characterId = characterId,
            characterDetailViewModel = characterDetailViewModel
        )
    }
}

@Composable
fun CharacterDetailsScreenContent(
    characterId: Int,
    characterDetailViewModel: CharacterDetailViewModel
) {
    val character by characterDetailViewModel.character.collectAsState()
    val isFavorite by characterDetailViewModel.isFavorite.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(characterId) {
        characterDetailViewModel.loadCharacter(characterId)
    }

    AppTopBar(
        characterName = character?.name ?: "",
        currentScreen = CharacterDetailsScreen(characterId),
        actions = {
            IconButton(onClick = {
                character?.let {
                    characterDetailViewModel.toggleFavorite()
                }
            }) {
                Icon(
                    painter = painterResource(if (isFavorite) R.drawable.favorites_selected else R.drawable.star_for_add),
                    contentDescription = "Favorite",
                    tint =
                    if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseSurface
                )
            }
        },
        onBackClick = {
            navigator.pop()
        }
    )
    character?.let { char ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, shape = MaterialTheme.shapes.extraLarge),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 30.dp
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = char.imageUrl,
                            contentDescription = "Character Image",
                            modifier = Modifier
                                .size(140.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = stringResource(R.string.name),
                                modifier = Modifier.padding(bottom = 18.dp),
                                color = MaterialTheme.colorScheme.outline,
                                style = Typography.headlineSmall
                            )
                            Text(
                                text = char.name,
                                style = Typography.headlineMedium,
                                maxLines = 2,
                                softWrap = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                    )

                    CharacterDetailItem(stringResource(R.string.status), char.status.replaceFirstChar { it.uppercase() })
                    CharacterDetailItem(stringResource(R.string.species), char.species)
                    CharacterDetailItem(stringResource(R.string.type), char.type.ifEmpty { "-" })
                    CharacterDetailItem(stringResource(R.string.gender), char.gender)
                    CharacterDetailItem(stringResource(R.string.origin), char.origin)
                    CharacterDetailItem(stringResource(R.string.location), char.location)
                }
            }
        }
    } ?: Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CharacterDetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.outline,
            style = Typography.bodySmall
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            style = Typography.headlineSmall,
            maxLines = 2,
            softWrap = true
        )
    }
}