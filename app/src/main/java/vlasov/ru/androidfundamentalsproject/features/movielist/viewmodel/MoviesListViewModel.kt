package vlasov.ru.androidfundamentalsproject.features.movielist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class MoviesListViewModel : ViewModel(){
    abstract val moviesStateOutput: LiveData<MoviesListViewState>
}