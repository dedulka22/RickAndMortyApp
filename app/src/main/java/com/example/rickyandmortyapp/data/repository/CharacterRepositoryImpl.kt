package com.example.rickyandmortyapp.data.repository

import RickAndMortyApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickyandmortyapp.data.paging.CharactersPagingSource
import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi
) : CharacterRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return getCharacters(null)
    }

    override fun searchCharacter(query: String): Flow<PagingData<Character>> {
        return getCharacters(query)
    }

    private fun getCharacters(query: String?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharactersPagingSource(api, query) }
        ).flow
    }

    override suspend fun getCharacterById(id: Int): Character {
        return api.getCharacterById(id).toDomain()
    }
}