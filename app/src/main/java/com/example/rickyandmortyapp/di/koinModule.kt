package com.example.rickyandmortyapp.di

import ApiClient
import RickAndMortyApi
import androidx.room.Room
import com.example.rickyandmortyapp.data.local.AppDatabase
import com.example.rickyandmortyapp.data.repository.CharacterRepositoryImpl
import com.example.rickyandmortyapp.data.repository.FavoriteRepositoryImpl
import com.example.rickyandmortyapp.data.repository.NetworkRepositoryImpl
import com.example.rickyandmortyapp.domain.repository.CharacterRepository
import com.example.rickyandmortyapp.domain.repository.FavoriteRepository
import com.example.rickyandmortyapp.domain.repository.NetworkRepository
import com.example.rickyandmortyapp.domain.usecase.CharacterDetailUseCase
import com.example.rickyandmortyapp.domain.usecase.CheckInternetConnectionUseCase
import com.example.rickyandmortyapp.domain.usecase.GetCharactersUseCase
import com.example.rickyandmortyapp.domain.usecase.SearchCharacterUseCase
import com.example.rickyandmortyapp.ui.viewmodel.CharacterDetailViewModel
import com.example.rickyandmortyapp.ui.viewmodel.CharactersViewModel
import com.example.rickyandmortyapp.ui.viewmodel.FavoritesViewModel
import com.example.rickyandmortyapp.ui.viewmodel.InternetViewModel
import com.example.rickyandmortyapp.ui.viewmodel.SearchViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factoryOf(::CharacterRepositoryImpl) bind CharacterRepository::class
    factoryOf(::FavoriteRepositoryImpl) bind FavoriteRepository::class
    factoryOf(::NetworkRepositoryImpl) bind NetworkRepository::class

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().favoriteCharacterDao() }

    single { GetCharactersUseCase(get()) }
    single { SearchCharacterUseCase(get()) }
    single { CharacterDetailUseCase(get(), get()) }
    single { CheckInternetConnectionUseCase(get()) }

    viewModelOf(::CharactersViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::CharacterDetailViewModel)
    viewModelOf(::InternetViewModel)

    single<RickAndMortyApi> { ApiClient.api }
}
