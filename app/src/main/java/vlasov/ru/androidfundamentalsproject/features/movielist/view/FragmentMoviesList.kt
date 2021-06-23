package vlasov.ru.androidfundamentalsproject.features.movielist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.features.movielist.viewmodel.MovieListViewModelFactory
import vlasov.ru.androidfundamentalsproject.features.movielist.viewmodel.MoviesListViewModelImpl
import vlasov.ru.androidfundamentalsproject.features.movielist.viewmodel.MoviesListViewState
import vlasov.ru.androidfundamentalsproject.models.Movie

class FragmentMoviesList : Fragment() {

    interface MoviesListEventListener{
        fun onMovieClickEvent(movie : Movie)
    }

    val viewModelImpl : MoviesListViewModelImpl by viewModels{
        MovieListViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    var moviesAdapter : MoviesListItemAdapter? = null
    var moviesListEventListener : MoviesListEventListener? = null
    var moviesListProgressBar : ProgressBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MoviesListEventListener){
            moviesListEventListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movies_list, container, false)

        moviesListProgressBar = root.findViewById(R.id.moviesListProgressBar)

        root.findViewById<RecyclerView>(R.id.moviesList).apply {
            layoutManager = GridLayoutManager(context, 2)
            moviesAdapter = MoviesListItemAdapter(object : MoviesListItemAdapter.MovieInListOnClickListener {
                override fun onMovieClick(movie: Movie) {
                    moviesListEventListener?.onMovieClickEvent(movie)
                }
            })
            adapter = moviesAdapter
        }
        loadDataToAdapter()
        return root
    }

    private fun loadDataToAdapter(){
        viewModelImpl.moviesStateOutput.observe(viewLifecycleOwner) {
            when(it){
                is MoviesListViewState.MoviesLoaded -> {
                    moviesAdapter?.submitList(it.movies)
                    moviesListProgressBar?.visibility = View.GONE
                }
                is MoviesListViewState.FailedToLoad -> Toast.makeText(
                    requireContext(),
                    R.string.error_network_failed,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        moviesAdapter = null
    }

    override fun onDetach() {
        super.onDetach()
        moviesListEventListener = null
    }


    companion object{
        val FRAGMENT_TAG = "vlasov.ru.androidfundamentalsproject.features.movielist.view.FragmentMoviesList"
    }
}