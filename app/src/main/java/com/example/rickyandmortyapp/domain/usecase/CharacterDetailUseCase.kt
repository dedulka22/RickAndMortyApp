package com.example.rickyandmortyapp.domain.usecase

import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.domain.repository.CharacterRepository
import com.example.rickyandmortyapp.domain.repository.FavoriteRepository

class CharacterDetailUseCase(
    private val repository: CharacterRepository,
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(id: Int) = repository.getCharacterById(id)
    fun isFavorite(characterId: Int) = favoriteRepository.isFavorite(characterId)
    suspend fun addToFavorites(character: Character) = favoriteRepository.addToFavorites(character)
    suspend fun removeFromFavorites(characterId: Int) = favoriteRepository.removeFromFavorites(characterId)
}