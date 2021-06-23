package vlasov.ru.androidfundamentalsproject.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vlasov.ru.androidfundamentalsproject.data.locale.LocalDataSource
import vlasov.ru.androidfundamentalsproject.models.Movie
import vlasov.ru.androidfundamentalsproject.models.runCatchingResult
import vlasov.ru.androidfundamentalsproject.models.Result
import vlasov.ru.androidfundamentalsproject.network.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localDataSource : LocalDataSource) : MovieRepository {

    override suspend fun loadMovies(): Result<List<Movie>> {
        return runCatchingResult {
            withContext(Dispatchers.IO) {
                val movieDB = localDataSource.loadMovies()
                if (movieDB.isEmpty()) {
                    val movieFromNetwork = remoteMovieDataSource.loadMovies()
                    localDataSource.insertMovies(movieFromNetwork)
                    movieFromNetwork
                } else {
                    movieDB
                }
            }
        }
    }
    override suspend fun loadMovie(movieId: Int): Movie? {
        TODO("Not yet implemented")
    }
}