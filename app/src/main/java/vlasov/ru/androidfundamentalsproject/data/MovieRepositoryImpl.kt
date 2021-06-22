package vlasov.ru.androidfundamentalsproject.data

import vlasov.ru.androidfundamentalsproject.models.Movie
import vlasov.ru.androidfundamentalsproject.network.RemoteMovieDataSource

class MovieRepositoryImpl(private val remoteMovieDataSource: RemoteMovieDataSource) : MovieRepository {

    override suspend fun loadMovies(): List<Movie> {
        return remoteMovieDataSource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int): Movie? {
        TODO("Not yet implemented")
    }
}