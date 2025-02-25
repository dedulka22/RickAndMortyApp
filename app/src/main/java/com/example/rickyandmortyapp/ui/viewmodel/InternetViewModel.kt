package com.example.rickyandmortyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickyandmortyapp.domain.usecase.CheckInternetConnectionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class InternetViewModel(
    checkInternetConnectionUseCase: CheckInternetConnectionUseCase
) : ViewModel() {

    val isInternetAvailable: StateFlow<Boolean> = checkInternetConnectionUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
}