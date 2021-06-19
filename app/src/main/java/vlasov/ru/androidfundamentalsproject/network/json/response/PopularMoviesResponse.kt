package vlasov.ru.androidfundamentalsproject.network.json.response

import kotlinx.serialization.SerialName

class PopularMoviesResponse(@SerialName("page") val page : Int,
    @SerialName("results") val moviesList : List<MovieResponse>)