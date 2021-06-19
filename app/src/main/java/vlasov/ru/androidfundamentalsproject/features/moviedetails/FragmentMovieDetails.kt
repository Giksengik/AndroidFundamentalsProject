package vlasov.ru.androidfundamentalsproject.features.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.databinding.FragmentMovieDetailsBinding
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.models.Movie

class FragmentMovieDetails : Fragment() {

    private val viewModel : MovieDetailsViewModel by viewModels{
        MovieDetailsViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var castAdapter: MovieCastAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_movie_details, container, false)
        val root = binding.root

        if(arguments != null){
                viewModel.setMovieData(requireArguments().getSerializable(MOVIE_ARG) as Movie?)
        }
        viewModel.movieData.observe(viewLifecycleOwner){
            setUIData(it)
        }
        root.findViewById<RecyclerView>(R.id.movieCastRecyclerView).apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            castAdapter = MovieCastAdapter()
            adapter = castAdapter
        }

        setUpListeners()

        return root
    }

    private fun setUIData(movie: Movie) {
        binding.movie = movie
        binding.root.findViewById<ImageView>(R.id.moviePicture).load(movie.detailImageUrl)
        castAdapter.submitList(movie.actors)
    }

    private fun setUpListeners(){
        binding.root.findViewById<ImageButton>(R.id.backButtonMovie).setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object{
        val MOVIE_ARG = "vlasov.ru.androidfundamentalsproject.features.moviedetails.FragmentMovieDetails.movie"
        val FRAGMENT_TAG = "vlasov.ru.androidfundamentalsproject.features.moviedetails.FragmentMovieDetails"
        fun newInstance(movie: Movie) : FragmentMovieDetails {
            val args = Bundle()
            args.putSerializable(MOVIE_ARG,movie)
            val fragment = FragmentMovieDetails()
            fragment.arguments = args
            return fragment
        }

    }
}