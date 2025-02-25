package com.example.rickyandmortyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickyandmortyapp.domain.model.Character

@Entity(tableName = "favorite_characters")
data class FavoriteCharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String
) {
    fun toDomain(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            species = species,
            gender = gender,
            origin = origin,
            imageUrl = imageUrl,
            type = type,
            location = location
        )
    }
}

fun Character.toEntity(): FavoriteCharacterEntity {
    return FavoriteCharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = origin,
        imageUrl = imageUrl,
        type = type,
        location = location
    )
}