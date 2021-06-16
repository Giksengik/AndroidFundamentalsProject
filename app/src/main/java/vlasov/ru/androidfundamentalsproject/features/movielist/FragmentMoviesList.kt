package vlasov.ru.androidfundamentalsproject.features.movielist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.features.moviedetails.MovieDetailsViewModelFactory
import vlasov.ru.androidfundamentalsproject.models.Movie

class FragmentMoviesList : Fragment() {

    interface MoviesListEventListener{
        fun onMovieClickEvent(movie : Movie)
    }

    val viewModel : MovieListViewModel by viewModels{
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

        viewModel.movieList.observe(viewLifecycleOwner, {
            setListData(it)
        })

        viewModel.loadingState.observe(viewLifecycleOwner, {
            if(it) setLoading()
            else setCompleteLoading()
        })
        viewModel.loadDataToAdapter()
        return root
    }

    private fun setLoading() {
        moviesListProgressBar?.visibility = View.VISIBLE
    }

    private fun setListData(movies : List<Movie>) {
        moviesAdapter?.submitList(movies)
    }

    private fun setCompleteLoading() {
        moviesListProgressBar?.visibility = View.GONE
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
        val FRAGMENT_TAG = "vlasov.ru.androidfundamentalsproject.features.movielist.FragmentMoviesList"
    }
}