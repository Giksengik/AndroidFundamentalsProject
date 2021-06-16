package vlasov.ru.androidfundamentalsproject.di

import vlasov.ru.androidfundamentalsproject.data.MovieRepository

internal interface MovieRepositoryProvider {
    fun provideMovieRepository(): MovieRepository
}