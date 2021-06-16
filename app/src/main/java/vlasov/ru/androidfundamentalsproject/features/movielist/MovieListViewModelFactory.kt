package vlasov.ru.androidfundamentalsproject.features.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vlasov.ru.androidfundamentalsproject.data.MovieRepository

class MovieListViewModelFactory(val repository: MovieRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MovieListViewModel(repository) as T

}