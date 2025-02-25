package com.example.rickyandmortyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickyandmortyapp.domain.model.Character
import com.example.rickyandmortyapp.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.Flow

class CharactersViewModel(
    getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val characters: Flow<PagingData<Character>> = getCharactersUseCase()
        .cachedIn(viewModelScope)
}