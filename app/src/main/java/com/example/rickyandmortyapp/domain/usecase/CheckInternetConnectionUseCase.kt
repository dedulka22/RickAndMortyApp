package com.example.rickyandmortyapp.domain.usecase

import com.example.rickyandmortyapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow

class CheckInternetConnectionUseCase(
    private val repository: NetworkRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isInternetAvailable()
}