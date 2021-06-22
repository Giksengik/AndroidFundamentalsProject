package vlasov.ru.androidfundamentalsproject.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import vlasov.ru.androidfundamentalsproject.models.Actor
import vlasov.ru.androidfundamentalsproject.models.Genre
import vlasov.ru.androidfundamentalsproject.models.Movie

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): Movie?
}
