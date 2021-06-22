package vlasov.ru.androidfundamentalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.data.MovieRepositoryImpl
import vlasov.ru.androidfundamentalsproject.di.MovieRepositoryProvider
import vlasov.ru.androidfundamentalsproject.di.NetworkModule
import vlasov.ru.androidfundamentalsproject.features.moviedetails.FragmentMovieDetails
import vlasov.ru.androidfundamentalsproject.features.movielist.FragmentMoviesList
import vlasov.ru.androidfundamentalsproject.models.Movie
import vlasov.ru.androidfundamentalsproject.network.RemoteMovieDataSource
import vlasov.ru.androidfundamentalsproject.network.json.DataSource

class MainActivity : AppCompatActivity(), MovieRepositoryProvider, FragmentMoviesList.MoviesListEventListener
{
    private val networkModule = NetworkModule()
    private val remoteDataSource = DataSource(networkModule.api)
    private val movieRepository = MovieRepositoryImpl(remoteDataSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentMoviesList())
                .commit()
    }

    override fun provideMovieRepository(): MovieRepository = movieRepository

    override fun onMovieClickEvent(movie: Movie) {
        supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentMovieDetails.newInstance(movie))
                .addToBackStack( FragmentMovieDetails.FRAGMENT_TAG)
                .commit()
    }


}