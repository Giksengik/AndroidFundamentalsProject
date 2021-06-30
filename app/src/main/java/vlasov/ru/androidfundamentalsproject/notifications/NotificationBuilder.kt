package vlasov.ru.androidfundamentalsproject.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import kotlinx.serialization.Serializable
import vlasov.ru.androidfundamentalsproject.R
import vlasov.ru.androidfundamentalsproject.features.MainActivity
import vlasov.ru.androidfundamentalsproject.models.Movie

@Serializable
class NotificationBuilder {
    companion object{
        const val NEW_FILM_CHANNEL_CODE = "vlasov.ru.androidfundamentalsproject.NEW_FILM"
        private const val REQUEST_CONTENT = 1
    }
    fun showNewMovieNotification(movie : Movie, context : Context) {
        val contentUri = "https://vlasov.ru.androidfundamentalsproject/movie/${movie.id}".toUri()

        val notificationBuilder = NotificationCompat.Builder(context,  NEW_FILM_CHANNEL_CODE).apply {
            setContentTitle(context.getString(R.string.new_film_notification_title ) + " " + movie.title)
            setSmallIcon(R.drawable.ic_new_movie)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setOnlyAlertOnce(true)
            setContentIntent(PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context,
                    MainActivity::class.java)
                            .setAction(Intent.ACTION_VIEW)
                            .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
            ))
        }

        NotificationManagerCompat.from(context).notify(movie.id, notificationBuilder.build())
    }
}