package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieResponse( @SerialName("adult") val isAdult : Boolean,
                     @SerialName("id") val id : Int,
    @SerialName("backdrop_path") val backgroundPath: String,
    @SerialName("genre_ids") val genreIds : List<Int>,
    @SerialName("original_language") val originalLanguage : String,
    @SerialName("original_title") val originalTitle : String,
    @SerialName("overview") val overview : String,
    @SerialName("popularity") val popularity : Double,
    @SerialName("poster_path") val posterPath : String,
    @SerialName("release_date") val releaseDate : String,
    @SerialName("title") val title : String,
    @SerialName("vote_average") val voteAverage : Double,
    @SerialName("vote_count") val voteCount : Int)