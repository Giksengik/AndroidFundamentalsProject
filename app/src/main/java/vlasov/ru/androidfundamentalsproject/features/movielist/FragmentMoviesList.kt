package vlasov.ru.androidfundamentalsproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import vlasov.ru.androidfundamentalsproject.models.Movie

class FragmentMoviesList : Fragment() {
    companion object{
        val FRAGMENT_TAG = "vlasov.ru.androidfundamentalsproject.FragmentMoviesList"
    }

    interface MoviesListEventListener{
        fun onMovieClickEvent(movie : Movie)
    }

    var moviesAdapter : MoviesListItemAdapter? = null
    var moviesListEventListener : MoviesListEventListener? = null

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
        val moviesList = root.findViewById<RecyclerView>(R.id.moviesList)

        moviesAdapter =
            MoviesListItemAdapter(object : MoviesListItemAdapter.MovieInListOnClickListener {
                override fun onMovieClick(movie: Movie) {
                    moviesListEventListener?.onMovieClickEvent(movie)
                }
            })

        moviesList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesAdapter
        }

        loadDataToAdapter()

        return root
    }


    private fun loadDataToAdapter() {
        val repo = (requireActivity() as MovieRepositoryProvider).provideMovieRepository()
        lifecycleScope.launch {
            val moviesData = repo.loadMovies()
            moviesAdapter?.submitList(moviesData)
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
}