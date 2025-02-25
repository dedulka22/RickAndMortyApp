package com.example.rickyandmortyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Query("SELECT * FROM favorite_characters")
    fun getAllFavorites(): Flow<List<FavoriteCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(character: FavoriteCharacterEntity)

    @Query("DELETE FROM favorite_characters WHERE id = :characterId")
    suspend fun removeFromFavorites(characterId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :characterId)")
    fun isFavorite(characterId: Int): Flow<Boolean>
}