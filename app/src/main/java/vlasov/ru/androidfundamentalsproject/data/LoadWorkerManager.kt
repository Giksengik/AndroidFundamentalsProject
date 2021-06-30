package vlasov.ru.androidfundamentalsproject.data

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit


class LoadWorkerManager {
    companion object {
        const val LOAD_WORK_NAME = "vlasov.ru.androidfundamentalsproject.LOAD_WORK_NAME"
    }
    fun createLoadTask(context: Context){
        val loadConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val loadRequest = PeriodicWorkRequest.Builder(LoadWorker::class.java, 4, TimeUnit.HOURS)
                .setConstraints(loadConstraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(LOAD_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                loadRequest)
    }
}