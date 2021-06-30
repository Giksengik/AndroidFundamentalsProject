package vlasov.ru.androidfundamentalsproject.data

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import vlasov.ru.androidfundamentalsproject.data.locale.LocalDataSource
import vlasov.ru.androidfundamentalsproject.network.RemoteMovieDataSource
import vlasov.ru.androidfundamentalsproject.notifications.NotificationBuilder

class LoadWorkerFactory(private val localDataSource: LocalDataSource, private val remoteMovieDataSource: RemoteMovieDataSource,
                        private val notificationBuilder: NotificationBuilder) : WorkerFactory() {
    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        return when(workerClassName){
            LoadWorker::class.java.name ->
                LoadWorker(appContext, workerParameters, localDataSource, remoteMovieDataSource, notificationBuilder)
            else -> null
        }
    }
}