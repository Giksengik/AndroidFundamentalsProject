package vlasov.ru.androidfundamentalsproject.network

import vlasov.ru.androidfundamentalsproject.models.Movie

interface RemoteMovieDataSource {
    fun  loadMovies() : List<Movie>;
}