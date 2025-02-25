package com.example.rickyandmortyapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun isInternetAvailable(): Flow<Boolean>
}