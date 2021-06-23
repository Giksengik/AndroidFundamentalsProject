package vlasov.ru.androidfundamentalsproject.data.locale.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieDB::class, GenreDB::class, ActorDB::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    companion object{

        private var INSTANCE : AppRoomDatabase? = null
        private const val DATABASE_NAME = "vlasov.ru.androidfundamentalsproject.data.locale.room.AppRoomDatabase"

        fun getInstance(context: Context) : AppRoomDatabase{
            synchronized(AppRoomDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppRoomDatabase::class.java,
                        DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()
                }
                return INSTANCE!!
            }
        }

    }

    abstract fun getMovieDao() : MovieDAO
}