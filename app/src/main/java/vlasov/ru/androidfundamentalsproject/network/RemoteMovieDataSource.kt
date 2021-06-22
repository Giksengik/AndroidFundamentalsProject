package vlasov.ru.androidfundamentalsproject.network

import vlasov.ru.androidfundamentalsproject.models.Movie

interface RemoteMovieDataSource {
   suspend fun  loadMovies() : List<Movie>;
}