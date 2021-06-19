package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponse(@SerialName("genres") val genres : List<GenresResponse>)