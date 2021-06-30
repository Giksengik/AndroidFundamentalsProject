package vlasov.ru.androidfundamentalsproject.data.locale.room

import androidx.room.*
import vlasov.ru.androidfundamentalsproject.models.Actor

@Dao
interface MovieDAO {

    @Query("Select * from movie")
    fun getMovies() : List<MovieWithGenresAndActorsDB>

    @Query("Select * from movie where id = :id")
    fun getMovie(id :Long) : MovieWithGenresAndActorsDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(item : MovieDB)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenre(item : GenreDB)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertActor(item : ActorDB)
}