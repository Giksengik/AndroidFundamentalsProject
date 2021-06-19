package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActorResponse ( @SerialName("also_known_as")val aliases : List<String>,
                      @SerialName("biography") val biography : String,
                      @SerialName("birthday") val birthday : String,
                      @SerialName("deathday") val deathday: String?,
                      @SerialName("id") val id : Int,
                      @SerialName("imdb_id") val imdbID : String,
                      @SerialName("known_for_department") val knownFor : String,
                      @SerialName("name") val name : String,
                      @SerialName("place_of_birth") val placeOfBirth : String,
                      @SerialName("profile_path") val profilePath : String
)