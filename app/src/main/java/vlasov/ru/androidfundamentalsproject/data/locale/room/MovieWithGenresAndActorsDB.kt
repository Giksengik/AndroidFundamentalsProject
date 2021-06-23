package vlasov.ru.androidfundamentalsproject.data.locale.room

import androidx.room.Embedded
import androidx.room.Relation

class MovieWithGenresAndActorsDB (
    @Embedded
    val movie : MovieDB,
    @Relation(parentColumn = "id", entityColumn = "parentId" )
    val genres : List<GenreDB>,
    @Relation(parentColumn = "id", entityColumn = "parentId" )
    val actors : List<ActorDB>
)
