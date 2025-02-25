package com.example.rickyandmortyapp.domain.usecase

import androidx.paging.PagingData
import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class SearchCharacterUseCase(
    private val repository: CharacterRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Character>> =
        repository.searchCharacter(query)
}