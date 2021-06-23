package vlasov.ru.androidfundamentalsproject.data

import vlasov.ru.androidfundamentalsproject.models.Result
import vlasov.ru.androidfundamentalsproject.models.Movie

interface MovieRepository {
    suspend fun loadMovies(): Result<List<Movie>>
    suspend fun loadMovie(movieId: Int): Movie?
}
