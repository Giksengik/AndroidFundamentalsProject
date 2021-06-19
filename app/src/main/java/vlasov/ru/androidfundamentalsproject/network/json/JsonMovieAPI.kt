package vlasov.ru.androidfundamentalsproject.network.json

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vlasov.ru.androidfundamentalsproject.network.json.response.ConfigurationResponse
import vlasov.ru.androidfundamentalsproject.network.json.response.GenresResponse
import vlasov.ru.androidfundamentalsproject.network.json.response.PopularMoviesResponse

interface JsonMovieAPI {
    companion object{
        val STANDART_LANGUAGE = "en-US"
        val STANDART_PAGE = 1
    }

    @GET("configuration")
    suspend fun loadConfiguration() : ConfigurationResponse

    @GET("genre/movie/list")
    suspend fun loadGenres(@Query("language") language : String = STANDART_LANGUAGE) : GenresResponse

    @GET("/movie/popular")
    suspend fun  loadPopularMovies(@Query("language") language : String = STANDART_LANGUAGE,
    @Query("page") page : Int = STANDART_PAGE) : PopularMoviesResponse

    @GET("/person/{person_id}")
    suspend fun  loadActorData(@Path("person_id") id: Int,
                               @Query("language") language : String = STANDART_LANGUAGE)

    @GET("/movie/{movie_id}/credits")
    suspend fun loadMovieCredits(@Path("movie_id") movieID : Int,
                                 @Query("language") language : String = STANDART_LANGUAGE)
    

}