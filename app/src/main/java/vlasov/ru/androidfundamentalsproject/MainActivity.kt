package vlasov.ru.androidfundamentalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() , FragmentMoviesList.MoviesListEventListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentMoviesList())
                .commit()
    }

    override fun onMovieClickEvent(movie: MovieDetails) {
        supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentMovieDetails.newInstance(movie))
                .addToBackStack( FragmentMovieDetails.FRAGMENT_TAG)
                .commit()
    }


}