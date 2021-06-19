package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieCreditsResponse(@SerialName("id") val id : Int,
    @SerialName("cast") val movieCast : List<ActorResponse>)