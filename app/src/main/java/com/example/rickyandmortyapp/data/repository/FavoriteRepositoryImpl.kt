package com.example.rickyandmortyapp.data.repository

import com.example.rickyandmortyapp.data.local.FavoriteCharacterDao
import com.example.rickyandmortyapp.data.local.toEntity
import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val dao: FavoriteCharacterDao
) : FavoriteRepository {

    override fun getAllFavorites(): Flow<List<Character>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addToFavorites(character: Character) {
        dao.addToFavorites(character.toEntity())
    }

    override suspend fun removeFromFavorites(characterId: Int) {
        dao.removeFromFavorites(characterId)
    }

    override fun isFavorite(characterId: Int): Flow<Boolean> {
        return dao.isFavorite(characterId)
    }
}