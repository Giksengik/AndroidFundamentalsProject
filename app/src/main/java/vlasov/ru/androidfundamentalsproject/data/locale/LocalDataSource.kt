package vlasov.ru.androidfundamentalsproject.data.locale

import vlasov.ru.androidfundamentalsproject.models.Movie

interface LocalDataSource {
    suspend fun loadMovies() : List<Movie>
    suspend fun insertMovies(movies : List<Movie>)
    suspend fun loadMovie(id : Int): Movie
}