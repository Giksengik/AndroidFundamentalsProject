package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ConfigurationResponse (
    @SerialName("images") val imageConfiguration: ImageConfigurationResponse,
    @SerialName("change_keys") val changeKeys : List<String>
    )