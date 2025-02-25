package com.example.rickyandmortyapp.domain.repository

import com.example.rickyandmortyapp.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<Character>>
    suspend fun addToFavorites(character: Character)
    suspend fun removeFromFavorites(characterId: Int)
    fun isFavorite(characterId: Int): Flow<Boolean>
}