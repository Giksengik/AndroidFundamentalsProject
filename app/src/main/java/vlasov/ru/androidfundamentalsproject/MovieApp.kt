package vlasov.ru.androidfundamentalsproject

import android.app.Application
import vlasov.ru.androidfundamentalsproject.data.locale.room.AppRoomDatabase

class MovieApp : Application() {
    companion object{
        lateinit var db : AppRoomDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = AppRoomDatabase.getInstance(context = applicationContext)
    }
}