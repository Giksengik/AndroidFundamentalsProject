package vlasov.ru.androidfundamentalsproject.features.movielist

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.models.Movie

class MoviesListViewModel(private val repository: MovieRepository) : ViewModel(), LifecycleObserver {

    private val mutableMovieList = MutableLiveData<List<Movie>>(emptyList())
    val movieList : LiveData<List<Movie>> get() = mutableMovieList;

    private val mutableLoadingState = MutableLiveData<Boolean>(false)
    val loadingState : LiveData<Boolean> get() = mutableLoadingState

    fun loadDataToAdapter() {

        mutableLoadingState.value = true
        viewModelScope.launch {
            val moviesData = repository.loadMovies()
            mutableMovieList.value = moviesData
        }
        mutableLoadingState.value = false

    }


}