package com.example.rickyandmortyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacterEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}