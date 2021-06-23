package vlasov.ru.androidfundamentalsproject.features.movielist.viewmodel

import vlasov.ru.androidfundamentalsproject.models.Movie

sealed class MoviesListViewState {

    data class MoviesLoaded(val movies: List<Movie>) : MoviesListViewState()

    data class FailedToLoad(val exception: Throwable) : MoviesListViewState()
}
