package vlasov.ru.androidfundamentalsproject

import android.os.Bundle
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import vlasov.ru.androidfundamentalsproject.databinding.FragmentMovieDetailsBinding

class FragmentMovieDetails : Fragment() {

    companion object{
        val MOVIE_ARG = "vlasov.ru.androidfundamentalsproject.FragmentMovieDetails.movie"
        val FRAGMENT_TAG = "vlasov.ru.androidfundamentalsproject.FragmentMovieDetails"
        fun newInstance(movie: MovieDetails) : FragmentMovieDetails{
            val args = Bundle();
            args.putSerializable(MOVIE_ARG,movie)
            val fragment = FragmentMovieDetails()
            fragment.arguments = args
            return fragment
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentMovieDetailsBinding= DataBindingUtil.inflate(
                inflater,R.layout.fragment_movie_details, container, false)
        val root = binding.root
        if(arguments != null){
                binding.movieDetails = requireArguments().getSerializable(MOVIE_ARG) as MovieDetails?
        }
        return root
    }
}