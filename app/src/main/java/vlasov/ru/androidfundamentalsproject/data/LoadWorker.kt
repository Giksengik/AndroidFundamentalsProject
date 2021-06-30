package vlasov.ru.androidfundamentalsproject.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import vlasov.ru.androidfundamentalsproject.MovieApp
import vlasov.ru.androidfundamentalsproject.data.locale.LocalDataSource
import vlasov.ru.androidfundamentalsproject.models.Genre
import vlasov.ru.androidfundamentalsproject.models.Movie
import vlasov.ru.androidfundamentalsproject.network.RemoteMovieDataSource
import vlasov.ru.androidfundamentalsproject.notifications.NotificationBuilder
import java.lang.Exception

class LoadWorker(context: Context, workerParams: WorkerParameters,
    private val localDataSource: LocalDataSource, private val remoteMovieDataSource: RemoteMovieDataSource, private val notificationBuilder: NotificationBuilder) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val movieFromNetwork = remoteMovieDataSource.loadMovies()
            Log.d("loading from work manag", "fgfg")
            val movieFromDB = remoteMovieDataSource.loadMovies()
            val newMovies: MutableList<Movie> = mutableListOf()
            for (item in movieFromNetwork) {
                if (!movieFromDB.contains(item))
                    newMovies.add(item)
            }
            if (newMovies.isNotEmpty()) {
                localDataSource.insertMovies(newMovies)
                MovieApp.context?.let {
                    notificationBuilder.showNewMovieNotification(findBestMovie(newMovies), it)
                }
            } else {
                MovieApp.context?.let {
                    notificationBuilder.showNewMovieNotification(findBestMovie(movieFromDB), it)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun findBestMovie(movies: List<Movie>): Movie {
        var bestMovie = movies[0]
        for (item in movies) {
            if (item.rating > bestMovie.rating)
                bestMovie = item
        }
        return bestMovie
    }
}