package vlasov.ru.androidfundamentalsproject

import vlasov.ru.androidfundamentalsproject.data.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}