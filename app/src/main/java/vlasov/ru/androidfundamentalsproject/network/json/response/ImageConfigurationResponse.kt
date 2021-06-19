package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ImageConfigurationResponse(
    @SerialName("base_url")val  baseUrl : String,
    @SerialName("secure_base_url") val secureBaseUrl : String,
    @SerialName("backdrop_sizes") val backdropSizes : List<String>,
    @SerialName("logo_sizes") val logoSizes : List<String>,
    @SerialName("poster_sizes") val posterSizes : List<String>,
    @SerialName("profile_sizes") val profileSizes : List<String>,
)