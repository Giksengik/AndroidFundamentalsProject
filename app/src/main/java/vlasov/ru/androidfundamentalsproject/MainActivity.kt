package vlasov.ru.androidfundamentalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import vlasov.ru.androidfundamentalsproject.data.JsonMovieRepository
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.models.Movie

class MainActivity : AppCompatActivity(), MovieRepositoryProvider, FragmentMoviesList.MoviesListEventListener
{
    private val jsonMovieRepository = JsonMovieRepository(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentMoviesList())
                .commit()
    }

    override fun provideMovieRepository(): MovieRepository = jsonMovieRepository

    override fun onMovieClickEvent(movie: Movie) {
        supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentMovieDetails.newInstance(movie))
                .addToBackStack( FragmentMovieDetails.FRAGMENT_TAG)
                .commit()
    }


}