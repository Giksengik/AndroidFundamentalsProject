package vlasov.ru.androidfundamentalsproject.features

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vlasov.ru.androidfundamentalsproject.MovieApp
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.features.moviedetails.FragmentMovieDetails
import vlasov.ru.androidfundamentalsproject.features.movielist.view.FragmentMoviesList
import vlasov.ru.androidfundamentalsproject.models.Movie

class MainActivity : AppCompatActivity(), MovieRepositoryProvider, FragmentMoviesList.MoviesListEventListener

{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.mainContainer, FragmentMoviesList())
                    .commit()
            intent?.let(::handleIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let(::handleIntent)
    }

    private fun handleIntent(intent: Intent) {
        when(intent.action){
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toLongOrNull()
                id?.let(::openMovieDetails)
            }
        }
    }
    private fun openMovieDetails(id : Long) {

        supportFragmentManager.popBackStack(FragmentMovieDetails.FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                val loadedMovie = MovieApp.movieRepository.loadMovie(id)

                withContext(Dispatchers.Main) {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.mainContainer, FragmentMoviesList())
                            .addToBackStack(FragmentMoviesList.FRAGMENT_TAG)
                            .commit()
                    loadedMovie?.let {
                        supportFragmentManager.beginTransaction()
                                .add(R.id.mainContainer, FragmentMovieDetails.newInstance(it))
                                .addToBackStack(FragmentMovieDetails.FRAGMENT_TAG)
                                .commit()
                    }
                }
                }
            }
        }


    override fun provideMovieRepository(): MovieRepository = MovieApp.movieRepository

    override fun onMovieClickEvent(movie: Movie) {
        supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentMovieDetails.newInstance(movie))
                .addToBackStack( FragmentMovieDetails.FRAGMENT_TAG)
                .commit()
    }
}