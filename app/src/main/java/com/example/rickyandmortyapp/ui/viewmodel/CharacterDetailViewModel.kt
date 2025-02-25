package com.example.rickyandmortyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.domain.usecase.CharacterDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterDetailUseCase: CharacterDetailUseCase,
) : ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite
        .stateIn(viewModelScope, SharingStarted.Lazily, false)


    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            val fetchedCharacter = characterDetailUseCase(characterId)
            _character.value = fetchedCharacter

            characterDetailUseCase.isFavorite(characterId).collectLatest { isFav ->
                _isFavorite.value = isFav
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            character.value?.let {
                if (_isFavorite.value) {
                    characterDetailUseCase.removeFromFavorites(it.id)
                } else {
                    characterDetailUseCase.addToFavorites(it)
                }
            }
        }
    }
}