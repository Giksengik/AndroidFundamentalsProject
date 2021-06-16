package vlasov.ru.androidfundamentalsproject.features.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.models.Movie

class MovieDetailsViewModel(val repository : MovieRepository) : ViewModel() {

    private val mutableMovieData = MutableLiveData<Movie>()
    val movieData get() = mutableMovieData

    fun setMovieData(movie : Movie?){

        movie?.let{
            mutableMovieData.value = it
        }

    }
}