package com.example.rickyandmortyapp.data.model

import com.example.rickyandmortyapp.domain.model.Character

data class CharacterResponse(
    val info: InfoDto,
    val results: List<CharacterDto>
)

data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,  // next = null -> doesn't have next page
    val prev: String?
)

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginDto,
    val location: LocationDto,
    val image: String
) {
    fun toDomain(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            species = species,
            gender = gender,
            origin = origin.name,
            imageUrl = image,
            type = type,
            location = location.name
        )
    }
}

data class LocationDto(
    val name: String
)

data class OriginDto(
    val name: String
)
