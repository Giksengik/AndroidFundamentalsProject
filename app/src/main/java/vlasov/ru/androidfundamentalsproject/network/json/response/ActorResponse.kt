package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ActorResponse (
                      @SerialName("id") val id : Int,
                      @SerialName("known_for_department") val knownFor : String,
                      @SerialName("name") val name : String,
                      @SerialName("profile_path") val profilePath : String?
)