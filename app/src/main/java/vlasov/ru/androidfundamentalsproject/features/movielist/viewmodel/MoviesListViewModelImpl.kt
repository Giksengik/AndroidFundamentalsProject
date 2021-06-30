package vlasov.ru.androidfundamentalsproject.features.movielist.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vlasov.ru.androidfundamentalsproject.MovieApp
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.models.Failure
import vlasov.ru.androidfundamentalsproject.models.Movie
import vlasov.ru.androidfundamentalsproject.models.Success
import vlasov.ru.androidfundamentalsproject.models.Result

class MoviesListViewModelImpl(private val repository: MovieRepository,
                              override val moviesStateOutput: MutableLiveData<MoviesListViewState> =
                                  MutableLiveData<MoviesListViewState>(),
) : MoviesListViewModel(), LifecycleObserver {


    init {
        loadMovies()
        MovieApp.context?.let {
            repository.setLoadWorker(it)
        }


    }

    private fun loadMovies() {
        viewModelScope.launch { handleResult(repository.loadMovies()) }
    }

    private fun handleResult(result: Result<List<Movie>>) {
        when (result) {
            is Success -> moviesStateOutput.postValue(MoviesListViewState.MoviesLoaded(result.data))
            is Failure -> moviesStateOutput.postValue(MoviesListViewState.FailedToLoad(result.exception))
        }.exhaustive
    }
}
val <T> T.exhaustive: T
    get() = this