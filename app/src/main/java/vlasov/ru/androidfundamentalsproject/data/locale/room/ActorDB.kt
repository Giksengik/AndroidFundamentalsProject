package vlasov.ru.androidfundamentalsproject.data.locale.room

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "Actor",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDB::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ActorDB (
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val parentId: Int
)