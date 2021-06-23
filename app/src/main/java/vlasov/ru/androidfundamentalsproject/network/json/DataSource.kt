package vlasov.ru.androidfundamentalsproject.network.json

import vlasov.ru.androidfundamentalsproject.models.Actor
import vlasov.ru.androidfundamentalsproject.models.Genre
import vlasov.ru.androidfundamentalsproject.models.Movie
import vlasov.ru.androidfundamentalsproject.network.RemoteMovieDataSource
import vlasov.ru.androidfundamentalsproject.network.json.response.ConfigurationResponse
import vlasov.ru.androidfundamentalsproject.network.json.response.ImageConfigurationResponse

class DataSource(private val api : JsonMovieAPI) : RemoteMovieDataSource {

    companion object{
        const val DEFAULT_SIZE = "original"
    }

    private var imageResponse : ImageConfigurationResponse? = null
    private var baseUrl : String? = null
    private var posterSize : String? = null
    private var backdropSize : String? = null
    private var profileSize : String? = null


    override suspend fun loadMovies(): List<Movie> {
        loadConfiguration()
        val genres = api.loadGenres().genres
        return api.loadPopularMovies().moviesList.map{ movie ->
         Movie(
                 id = movie.id,
                 pgAge = if(movie.isAdult) 16 else 13,
                 title = movie.title,
                 genres = genres.filter{
                     movie.genreIds.contains(it.id)
                 }.map{
                     Genre(
                             id = it.id,
                             name = it.name
                     )
                 },
                 runningTime = 11,
                 reviewCount = movie.voteCount ?: 0,
                 isLiked = false,
                 rating = movie.voteAverage,
                 imageUrl = formingUrl(baseUrl, posterSize, movie.posterPath),
                 detailImageUrl = formingUrl(baseUrl, backdropSize, movie.backgroundPath),
                 storyLine = movie.overview,
                 actors = api.loadMovieCredits(movie.id).movieCast.map {
                     Actor(
                             id = it.id,
                             name = it.name,
                             imageUrl = formingUrl(baseUrl, profileSize, it.profilePath)
                     )
                 }
         )
        }
    }

    private fun formingUrl(url: String?, size: String?, path: String?) : String? {
        return if (url == null || path == null) {
            null
        } else {
            url.plus(size.takeUnless { it.isNullOrEmpty() }?: DEFAULT_SIZE)
                    .plus(path)
        }
    }

    private suspend fun loadConfiguration(){
        if(imageResponse == null) {
            imageResponse = api.loadConfiguration().imageConfiguration
            baseUrl = imageResponse?.baseUrl
            posterSize = imageResponse?.posterSizes?.find{it == "w500"}
            backdropSize = imageResponse?.backdropSizes?.find {it == "w780"}
            profileSize = imageResponse?.profileSizes?.find {it == "w185"}
        }
    }

}