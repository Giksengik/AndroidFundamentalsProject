package vlasov.ru.androidfundamentalsproject.features.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vlasov.ru.androidfundamentalsproject.data.MovieRepository

class MovieDetailsViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MovieDetailsViewModel(repository) as T
}