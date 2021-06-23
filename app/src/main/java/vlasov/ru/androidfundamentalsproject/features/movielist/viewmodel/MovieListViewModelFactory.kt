package vlasov.ru.androidfundamentalsproject.features.movielist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vlasov.ru.androidfundamentalsproject.data.MovieRepository

class MovieListViewModelFactory(val repository: MovieRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MoviesListViewModelImpl(repository) as T

}