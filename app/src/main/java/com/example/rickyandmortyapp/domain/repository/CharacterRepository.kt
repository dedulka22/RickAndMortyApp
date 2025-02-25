package com.example.rickyandmortyapp.domain.repository

import androidx.paging.PagingData
import com.example.rickyandmortyapp.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
    fun searchCharacter(query: String): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): Character
}