package vlasov.ru.androidfundamentalsproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import vlasov.ru.androidfundamentalsproject.data.LoadWorkerFactory
import vlasov.ru.androidfundamentalsproject.data.LoadWorkerManager
import vlasov.ru.androidfundamentalsproject.data.MovieRepository
import vlasov.ru.androidfundamentalsproject.data.MovieRepositoryImpl
import vlasov.ru.androidfundamentalsproject.data.locale.room.AppRoomDatabase
import vlasov.ru.androidfundamentalsproject.data.locale.room.RoomDataSource
import vlasov.ru.androidfundamentalsproject.di.NetworkModule
import vlasov.ru.androidfundamentalsproject.network.json.DataSource
import vlasov.ru.androidfundamentalsproject.notifications.NotificationBuilder
import vlasov.ru.androidfundamentalsproject.notifications.NotificationBuilder.Companion.NEW_FILM_CHANNEL_CODE

class  MovieApp : Application(){
    companion object {
        lateinit var db: AppRoomDatabase
        lateinit var localDataSource : RoomDataSource
        lateinit var movieRepository : MovieRepository
        val networkModule = NetworkModule()
        val remoteDataSource = DataSource(networkModule.api)
        val notificationBuilder = NotificationBuilder()
        val loadWorkerManager = LoadWorkerManager()
        var context: Context? = null

        fun isNetworkAvailable(): Boolean {
            if (context == null) return false
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
        }

    }

    override fun onCreate() {
        super.onCreate()

        db = AppRoomDatabase.getInstance(applicationContext)
        localDataSource = RoomDataSource(db)
        movieRepository = MovieRepositoryImpl(remoteDataSource, localDataSource, loadWorkerManager)
        context = applicationContext
        configWorkManager()
        createNotificationChannels()
    }

    override fun onTerminate() {
        context = null
        super.onTerminate()
    }

    private fun createNotificationChannels() {

        if (NotificationManagerCompat.from(applicationContext).getNotificationChannel(NEW_FILM_CHANNEL_CODE) == null) {
            val newFilmChannelBuilder = NotificationChannelCompat.Builder(NEW_FILM_CHANNEL_CODE, NotificationManagerCompat.IMPORTANCE_DEFAULT)
            newFilmChannelBuilder.apply {
                setName(getString(R.string.new_film_notification_name))
                setDescription(getString(R.string.new_film_notification_description))
            }
            NotificationManagerCompat.from(applicationContext).createNotificationChannel(newFilmChannelBuilder.build())
        }
    }

    private fun configWorkManager(){
        val loadWorkerFactory = DelegatingWorkerFactory()
        loadWorkerFactory.addFactory(LoadWorkerFactory(localDataSource, remoteDataSource, notificationBuilder))

        val config = Configuration.Builder()
                .setMinimumLoggingLevel(Log.INFO)
                .setWorkerFactory(loadWorkerFactory)
                .build()
        WorkManager.initialize(applicationContext, config)
    }
}

