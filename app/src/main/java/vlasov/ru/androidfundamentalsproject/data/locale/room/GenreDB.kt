package vlasov.ru.androidfundamentalsproject.data.locale.room

import androidx.room.Entity
import androidx.room.ForeignKey
import vlasov.ru.androidfundamentalsproject.models.Movie

@Entity(
    tableName = "Genre",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDB::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class GenreDB (val id: Int, val name: String, val parentId: Int)