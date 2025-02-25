package com.example.rickyandmortyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    val favoriteCharacters: StateFlow<List<Character>> = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun isFavorite(characterId: Int): StateFlow<Boolean> {
        return repository.isFavorite(characterId).stateIn(viewModelScope, SharingStarted.Lazily, false)
    }
}