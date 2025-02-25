import com.example.rickyandmortyapp.data.model.CharacterDto
import com.example.rickyandmortyapp.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponse

    @GET("character")
    suspend fun getSearchCharacter(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDto
}
