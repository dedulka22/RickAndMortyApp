package com.example.rickyandmortyapp.data.paging

import RickAndMortyApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickyandmortyapp.domain.model.Character

class CharactersPagingSource(
    private val api: RickAndMortyApi,
    private val query: String? = null
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 1

        return try {
            val response = if (query.isNullOrEmpty()) {
                api.getCharacters(page)
            } else {
                api.getSearchCharacter(page, query)
            }

            val characters = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
