package vlasov.ru.androidfundamentalsproject

import android.os.Bundle
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import vlasov.ru.androidfundamentalsproject.databinding.FragmentMovieDetailsBinding
import vlasov.ru.androidfundamentalsproject.models.Genre
import vlasov.ru.androidfundamentalsproject.models.Movie
import java.lang.StringBuilder

class FragmentMovieDetails : Fragment() {

    companion object{
        val MOVIE_ARG = "vlasov.ru.androidfundamentalsproject.FragmentMovieDetails.movie"
        val FRAGMENT_TAG = "vlasov.ru.androidfundamentalsproject.FragmentMovieDetails"
        fun newInstance(movie: Movie) : FragmentMovieDetails{
            val args = Bundle()
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
                binding.movie = requireArguments().getSerializable(MOVIE_ARG) as Movie?
                (requireArguments().getSerializable(MOVIE_ARG) as Movie?).let{
                    if (it != null) {
                        root.findViewById<ImageView>(R.id.moviePicture).load(it.detailImageUrl)
                    }
                }

        }
        root.findViewById<ImageButton>(R.id.backButtonMovie).setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        return root
    }

}