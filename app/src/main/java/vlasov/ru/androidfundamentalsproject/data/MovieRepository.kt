package vlasov.ru.androidfundamentalsproject.data

import android.content.Context
import kotlinx.serialization.Serializable
import vlasov.ru.androidfundamentalsproject.models.Result
import vlasov.ru.androidfundamentalsproject.models.Movie

interface MovieRepository {
    suspend fun loadMovies(): Result<List<Movie>>
    suspend fun loadMovie(movieId: Long): Movie?
    fun setLoadWorker(context: Context)
}
